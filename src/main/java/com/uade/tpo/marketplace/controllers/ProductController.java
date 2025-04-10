package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.dto.ProductRequest;
import com.uade.tpo.marketplace.entity.dto.ProductResponse;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.service.ProductService;
import com.uade.tpo.marketplace.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Traer todos los productos
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<Product> productos = productService.getAllProducts();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product p : productos) {
            responses.add(new ProductResponse(
                p.getId(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getCategory().getDescription()
            ));
        }

        return responses;
    }


    // Traer productos con stock
    @GetMapping("/available")
    public List<Product> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    // Filtrar por categoría
    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // Filtrar por rango de precio
    @GetMapping("/price")
    public List<Product> getByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return productService.getProductsByPriceRange(min, max);
    }

    // Crear un producto nuevo
@PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Optional<Category> categoryOpt = categoryRepository.findById(productRequest.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Product saved = productService.createProduct(
            productRequest.getDescription(),
            productRequest.getPrice(),
            productRequest.getStock(),
            categoryOpt.get()
        );

        ProductResponse response = new ProductResponse(
            saved.getId(),
            saved.getDescription(),
            saved.getPrice(),
            saved.getStock(),
            saved.getCategory().getDescription()
        );

        return ResponseEntity.ok(response);
    }

}

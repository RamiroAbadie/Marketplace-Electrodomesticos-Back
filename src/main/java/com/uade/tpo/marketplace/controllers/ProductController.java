package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.dto.ProductRequest;
import com.uade.tpo.marketplace.entity.dto.ProductResponse;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.service.ProductService;

import jakarta.validation.Valid;

import com.uade.tpo.marketplace.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        return productService.getAllProducts();
    }


    // Traer productos con stock
    @GetMapping("/available")
    public List<ProductResponse> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    // Filtrar por categoría
    @GetMapping("/product/{productId}")
    public List<ProductResponse> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // Filtrar por rango de precio
    @GetMapping("/price-range")
    public List<ProductResponse> getByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return productService.getProductsByPriceRange(min, max);
    }
    // Filtrar por precios menores
    @GetMapping("/price-less")
    public List<ProductResponse> getByPriceLess(@RequestParam BigDecimal min) {
        return productService.getProductsByPriceLess(min);
    }


    // Crear un producto nuevo
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
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

        ProductResponse response = productService.mapToDto(saved);

        return ResponseEntity.ok(response);
    }

    // Eliminar un producto por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<ProductResponse> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        productService.deleteProductById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Actualizar producto por id
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                        @RequestBody @Valid ProductRequest productRequest) {
        Optional<Product> existingProduct = productService.getEntityById(id);
        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Category> categoryOpt = categoryRepository.findById(productRequest.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Product  updatedProduct = existingProduct.get();
        updatedProduct.setDescription(productRequest.getDescription());
        updatedProduct.setPrice(productRequest.getPrice());
        updatedProduct.setStock(productRequest.getStock());
        updatedProduct.setCategory(categoryOpt.get());

        Product saved = productService.save(updatedProduct);

        ProductResponse response = productService.mapToDto(saved);

        return ResponseEntity.ok(response);
    }
}

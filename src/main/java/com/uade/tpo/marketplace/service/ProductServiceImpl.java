package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ProductImage;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.ProductResponse;
import com.uade.tpo.marketplace.repository.OrderItemRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public ProductResponse mapToDto(Product product) {
        List<String> imageList = new ArrayList<>();

        if (product.getImages() != null) {
            for (ProductImage img : product.getImages()) {
                String base64Image = Base64.getEncoder().encodeToString(img.getImage());
                imageList.add(base64Image);
            }
        }

        return new ProductResponse(
            product.getId(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getCategory().getDescription(),
            imageList
        );
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPriceLess(BigDecimal min) {
        return productRepository.findByPriceLessThan(min)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(String description, BigDecimal price, Integer stock, Category category) {
        Product product = new Product();
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToDto);
    }

    @Override
    public Optional<Product> getEntityById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (orderItemRepository.existsByProduct(product)) {
            throw new RuntimeException("No se puede eliminar el producto porque ya fue comprado en una orden.");
        }

        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}

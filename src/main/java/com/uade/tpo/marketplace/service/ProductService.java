package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getAvailableProducts();

    List<Product> getProductsByCategory(Long categoryId);

    List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max);

    Product createProduct(String description, BigDecimal price, Integer stock, Category category);
    
    Optional<Product> getProductById(Long id);
}

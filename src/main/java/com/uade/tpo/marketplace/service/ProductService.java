package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.ProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<ProductResponse> getAllProducts();

    public List<ProductResponse> getAvailableProducts();

    public List<ProductResponse> getProductsByCategory(Long categoryId);

    public List<ProductResponse> getProductsByPriceRange(BigDecimal min, BigDecimal max);

    public List<ProductResponse> getProductsByPriceLess(BigDecimal min);

    public Product createProduct(String description, BigDecimal price, Integer stock, Category category);
    
    public Optional<ProductResponse> getProductById(Long productId);

    Optional<Product> getEntityById(Long id);

    public void deleteProductById(Long id);

    public Product save(Product product);

    public ProductResponse mapToDto(Product product);

    List<ProductResponse> searchProducts(String keyword);

}

package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
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
    //Optional<T>	Evita errores por null. Te obliga a manejar el caso “no existe”
    public Optional<Product> getProductById(Long id) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        return productRepository.findById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        return productRepository.save(product);
    }
}

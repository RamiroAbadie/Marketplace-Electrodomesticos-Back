package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Traer solo productos con stock > 0
    List<Product> findByStockGreaterThan(Integer stock);

    // Buscar por categoría
    List<Product> findByCategoryId(Long categoryId);

    // Productos por precio menor a X
    List<Product> findByPriceLessThan(BigDecimal price);

    // Productos entre dos precios
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Busca “lo que contenga” la palabra clave, sin importar mayúsc/minúsculas
    List<Product> findByDescriptionContainingIgnoreCase(String keyword);

}


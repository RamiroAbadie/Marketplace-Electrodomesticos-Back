package com.uade.tpo.marketplace.entity.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String categoryDescription;

    public ProductResponse(Long id, String description, BigDecimal price, Integer stock, String categoryDescription) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryDescription = categoryDescription;
    }
}

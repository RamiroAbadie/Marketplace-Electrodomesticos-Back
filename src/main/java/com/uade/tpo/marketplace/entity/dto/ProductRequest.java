package com.uade.tpo.marketplace.entity.dto;

import java.math.BigDecimal;

import lombok.Data;

//Este DTO es solo para recibir datos del cliente cuando quiere crear (o editar) un producto
@Data
public class ProductRequest {
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
}

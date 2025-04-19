package com.uade.tpo.marketplace.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductResponse {
    private Long productId;
    private String description;
    private Integer quantity;
    private String category;
    private String unitPrice;
}

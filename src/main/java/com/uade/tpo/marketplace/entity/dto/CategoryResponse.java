package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String description;

    public CategoryResponse(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}

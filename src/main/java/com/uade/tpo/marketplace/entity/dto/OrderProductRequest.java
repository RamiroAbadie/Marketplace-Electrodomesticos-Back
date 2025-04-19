package com.uade.tpo.marketplace.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Esta clase se SOLAMENTE dentro de OrderRequest
@Data
public class OrderProductRequest {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productId;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}

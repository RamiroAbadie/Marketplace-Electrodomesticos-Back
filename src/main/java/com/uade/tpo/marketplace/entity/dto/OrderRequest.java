package com.uade.tpo.marketplace.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "La lista de productos no puede ser nula")
    private List<OrderProductRequest> items;
}

package com.uade.tpo.marketplace.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
//Este DTO es solo para recibir datos del cliente cuando quiere crear (o editar) un producto
@Data
public class CategoryRequest {
    @NotBlank(message = "La descripción de la categoría no puede estar vacía")
    private String description;
}


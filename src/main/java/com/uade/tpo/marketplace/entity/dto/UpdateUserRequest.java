package com.uade.tpo.marketplace.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstname;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastname;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}

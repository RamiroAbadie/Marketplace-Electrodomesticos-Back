package com.uade.tpo.marketplace.entity.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstname;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastname;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}

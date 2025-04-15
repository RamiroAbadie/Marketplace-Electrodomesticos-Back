package com.uade.tpo.exceptions;
// hola
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "La categoría no puede ser eliminada porque tiene productos asociados")
public class CategoryHasProductsException extends RuntimeException {
}


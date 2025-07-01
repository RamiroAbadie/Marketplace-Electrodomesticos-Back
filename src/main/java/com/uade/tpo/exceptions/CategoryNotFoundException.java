package com.uade.tpo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "La categoría no fue encontrada")
public class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException() {
        super("La categoría no fue encontrada");
    }

    public CategoryNotFoundException(Long id) {
        super("La categoría no fue encontrada (id = " + id + ")");
    }

    public CategoryNotFoundException(String description) {
        super("La categoría \"" + description + "\" no fue encontrada");
    }
}

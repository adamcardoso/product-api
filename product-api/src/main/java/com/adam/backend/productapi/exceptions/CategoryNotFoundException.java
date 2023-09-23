package com.adam.backend.productapi.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Categoria não encontrada.");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


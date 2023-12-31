package com.adam.backend.productapi.exceptions;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


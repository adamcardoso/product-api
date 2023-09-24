package com.adam.backend.productapi.exceptions;

import java.io.Serial;

public class DatabaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
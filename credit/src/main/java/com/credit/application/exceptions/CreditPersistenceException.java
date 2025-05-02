package com.credit.application.exceptions;

public class CreditPersistenceException extends RuntimeException {
    public CreditPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

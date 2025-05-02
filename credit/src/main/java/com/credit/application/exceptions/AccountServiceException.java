package com.credit.application.exceptions;

public class AccountServiceException extends RuntimeException {
    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
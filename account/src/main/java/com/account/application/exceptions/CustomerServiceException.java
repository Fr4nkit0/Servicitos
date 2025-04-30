package com.account.application.exceptions;

public class CustomerServiceException extends AccountServiceException {
    public CustomerServiceException(String message) {
        super(message);
    }

    public CustomerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
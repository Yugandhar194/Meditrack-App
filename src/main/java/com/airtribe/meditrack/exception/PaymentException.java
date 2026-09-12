package com.airtribe.meditrack.exception;

public class PaymentException extends Exception {
    private static final long serialVersionUID = 1L;
    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}

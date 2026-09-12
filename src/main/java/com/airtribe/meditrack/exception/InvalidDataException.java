package com.airtribe.meditrack.exception;

public class InvalidDataException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

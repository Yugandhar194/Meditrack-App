package com.airtribe.meditrack.exception;

public class AppointmentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

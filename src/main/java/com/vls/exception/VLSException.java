package com.vls.exception;

public class VLSException extends Exception {
    public VLSException(String message) {
        super(message);
    }

    public VLSException(String message, Throwable cause) {
        super(message, cause);
    }
}
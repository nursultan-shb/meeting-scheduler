package com.scheduler.demo.api.exceptions;

public class DtoValidationException extends RuntimeException {
    public DtoValidationException(String message) {
        super(message);
    }
}

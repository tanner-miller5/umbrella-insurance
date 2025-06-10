package com.umbrella.insurance.core.models.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}

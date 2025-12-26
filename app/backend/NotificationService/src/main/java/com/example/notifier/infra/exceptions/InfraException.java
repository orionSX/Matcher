package com.example.notifier.infra.exceptions;

public class InfraException extends RuntimeException {
    public InfraException(String message) {
        super(message);
    }
}

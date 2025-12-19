package com.example.userservice.infra.exceptions;

public class InfraException extends RuntimeException {
    public InfraException(String message) {
        super(message);
    }
}

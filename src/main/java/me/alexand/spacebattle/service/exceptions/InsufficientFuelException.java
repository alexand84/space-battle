package me.alexand.spacebattle.service.exceptions;

public class InsufficientFuelException extends RuntimeException {
    public InsufficientFuelException(String message) {
        super(message);
    }

    public InsufficientFuelException(String message, Throwable cause) {
        super(message, cause);
    }
}

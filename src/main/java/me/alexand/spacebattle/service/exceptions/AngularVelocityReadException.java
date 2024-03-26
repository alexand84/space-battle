package me.alexand.spacebattle.service.exceptions;

public class AngularVelocityReadException extends RuntimeException {
    public AngularVelocityReadException(String message) {
        super(message);
    }

    public AngularVelocityReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

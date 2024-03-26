package me.alexand.spacebattle.service.exceptions;

public class VelocityReadException extends RuntimeException {
    public VelocityReadException(String message) {
        super(message);
    }

    public VelocityReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

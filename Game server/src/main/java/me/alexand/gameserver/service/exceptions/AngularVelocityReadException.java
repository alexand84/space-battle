package me.alexand.gameserver.service.exceptions;

public class AngularVelocityReadException extends RuntimeException {
    public AngularVelocityReadException(String message) {
        super(message);
    }

    public AngularVelocityReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

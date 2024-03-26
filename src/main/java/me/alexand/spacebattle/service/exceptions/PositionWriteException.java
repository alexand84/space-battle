package me.alexand.spacebattle.service.exceptions;

public class PositionWriteException extends RuntimeException {
    public PositionWriteException(String message) {
        super(message);
    }

    public PositionWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

package me.alexand.gameserver.service.exceptions;

public class PositionReadException extends RuntimeException {
    public PositionReadException(String message) {
        super(message);
    }

    public PositionReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

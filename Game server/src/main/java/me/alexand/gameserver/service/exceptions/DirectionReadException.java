package me.alexand.gameserver.service.exceptions;

public class DirectionReadException extends RuntimeException {
    public DirectionReadException(String message) {
        super(message);
    }

    public DirectionReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

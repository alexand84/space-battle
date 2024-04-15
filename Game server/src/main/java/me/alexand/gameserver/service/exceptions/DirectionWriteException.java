package me.alexand.gameserver.service.exceptions;

public class DirectionWriteException extends RuntimeException {
    public DirectionWriteException(String message) {
        super(message);
    }

    public DirectionWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

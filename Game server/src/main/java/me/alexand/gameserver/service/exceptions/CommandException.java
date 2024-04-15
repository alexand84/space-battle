package me.alexand.gameserver.service.exceptions;

public class CommandException extends RuntimeException {

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

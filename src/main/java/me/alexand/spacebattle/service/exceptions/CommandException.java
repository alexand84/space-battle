package me.alexand.spacebattle.service.exceptions;

public class CommandException extends RuntimeException {

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

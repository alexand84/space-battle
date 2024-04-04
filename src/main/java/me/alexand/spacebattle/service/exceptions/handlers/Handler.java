package me.alexand.spacebattle.service.exceptions.handlers;

import me.alexand.spacebattle.service.ICommand;

import java.util.function.BiFunction;

public interface Handler extends BiFunction<ICommand, Throwable, ICommand> {
}

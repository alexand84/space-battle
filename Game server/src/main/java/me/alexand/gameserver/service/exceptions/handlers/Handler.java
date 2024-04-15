package me.alexand.gameserver.service.exceptions.handlers;


import me.alexand.gameserver.service.ICommand;

import java.util.function.BiFunction;

public interface Handler extends BiFunction<ICommand, Throwable, ICommand> {
}

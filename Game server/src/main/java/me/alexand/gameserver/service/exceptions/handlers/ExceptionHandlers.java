package me.alexand.gameserver.service.exceptions.handlers;


import me.alexand.gameserver.service.ICommand;

public interface ExceptionHandlers {

    void register(Class<? extends ICommand> commandType,
                  Class<? extends Throwable> exceptionType,
                  Handler handler);

    ICommand handle(ICommand command, Throwable throwable);

}

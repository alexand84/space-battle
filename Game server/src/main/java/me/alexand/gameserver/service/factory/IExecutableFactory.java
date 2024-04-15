package me.alexand.gameserver.service.factory;


import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;
import me.alexand.gameserver.service.IExecutable;

public interface IExecutableFactory {

    IExecutable create(ICommandsExecutor commandsExecutor, ICommand command);

}

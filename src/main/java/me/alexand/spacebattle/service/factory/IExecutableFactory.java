package me.alexand.spacebattle.service.factory;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ICommandsExecutor;
import me.alexand.spacebattle.service.IExecutable;

public interface IExecutableFactory {

    IExecutable create(ICommandsExecutor commandsExecutor, ICommand command);

}

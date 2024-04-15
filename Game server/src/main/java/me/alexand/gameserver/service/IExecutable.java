package me.alexand.gameserver.service;

public interface IExecutable {

    ICommandsExecutor getCommandExecutor();

    ICommand getCommand();

}

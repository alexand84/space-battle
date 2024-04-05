package me.alexand.spacebattle.service;

public interface IExecutable {

    ICommandsExecutor getCommandExecutor();

    ICommand getCommand();

}

package me.alexand.gameserver.service;

public interface ICommandsExecutor {

    void submit(ICommand command);

    void run();

    void stop();

}

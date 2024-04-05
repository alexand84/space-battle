package me.alexand.spacebattle.service;

public interface ICommandsExecutor {

    void submit(ICommand command);

    void run();

    void stop();

}

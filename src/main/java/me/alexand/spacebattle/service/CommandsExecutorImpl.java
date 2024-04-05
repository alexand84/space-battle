package me.alexand.spacebattle.service;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.exceptions.handlers.ExceptionHandlers;

import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class CommandsExecutorImpl implements ICommandsExecutor {
    private final BlockingQueue<ICommand> commandsQueue;
    private final ExceptionHandlers exceptionHandlers;
    private volatile boolean isRunning = false;

    @Override
    public void submit(ICommand command) {
        commandsQueue.add(command);
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                execute(commandsQueue.take());
            } catch (InterruptedException e) {
                stop();
            }
        }
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    private void execute(ICommand command) {
        try {
            command.execute();
        } catch (Throwable t) {
            execute(exceptionHandlers.handle(command, t));
        }
    }

}

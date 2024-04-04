package me.alexand.spacebattle.service.exceptions.handlers;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ICommandsExecutor;
import me.alexand.spacebattle.service.IExecutable;
import me.alexand.spacebattle.service.IRetryable;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import me.alexand.spacebattle.service.factory.IExecutableFactory;
import me.alexand.spacebattle.service.factory.IRetryableFactory;

@RequiredArgsConstructor
public class SubmitRetryCommandHandler implements Handler {
    private final ICommandsExecutor commandsExecutor;
    private final IRetryableFactory retryableFactory;
    private final ICommandFactory commandFactory;
    private final IExecutableFactory executableFactory;

    @Override
    public ICommand apply(ICommand failedCommand, Throwable throwable) {
        IRetryable retryable = retryableFactory.create(failedCommand);
        ICommand retryCommand = commandFactory.createRetryCommand(retryable);
        IExecutable executable = executableFactory.create(commandsExecutor, retryCommand);
        return commandFactory.createSubmitCommand(executable);
    }

}

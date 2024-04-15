package me.alexand.gameserver.service.exceptions.handlers;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;
import me.alexand.gameserver.service.IExecutable;
import me.alexand.gameserver.service.IRetryable;
import me.alexand.gameserver.service.factory.ICommandFactory;
import me.alexand.gameserver.service.factory.IExecutableFactory;
import me.alexand.gameserver.service.factory.IRetryableFactory;

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

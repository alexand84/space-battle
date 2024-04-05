package me.alexand.spacebattle.service.exceptions.handlers;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ICommandsExecutor;
import me.alexand.spacebattle.service.IExecutable;
import me.alexand.spacebattle.service.ILoggable;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import me.alexand.spacebattle.service.factory.IExecutableFactory;
import me.alexand.spacebattle.service.factory.ILoggableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class SubmitWriteLogCommandHandler implements Handler {
    private final ICommandsExecutor commandsExecutor;
    private final ILoggableFactory loggableFactory;
    private final ICommandFactory commandFactory;
    private final IExecutableFactory executableFactory;

    @Override
    public ICommand apply(ICommand failedCommand, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(failedCommand.getClass());
        ILoggable loggable = loggableFactory.create(logger, throwable);
        ICommand writeLogCommand = commandFactory.createWriteLogCommand(loggable);
        IExecutable executable = executableFactory.create(commandsExecutor, writeLogCommand);
        return commandFactory.createSubmitCommand(executable);
    }

}

package me.alexand.spacebattle.service.exceptions.handlers;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.repository.ICommandFailedStatRepository;
import me.alexand.spacebattle.service.*;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import me.alexand.spacebattle.service.factory.IExecutableFactory;
import me.alexand.spacebattle.service.factory.ILoggableFactory;
import me.alexand.spacebattle.service.factory.IRetryableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class RetryHandler implements Handler {

    private final ICommandsExecutor executor;
    private final long maxRetry;
    private final ICommandFactory commandFactory;
    private final IRetryableFactory retryableFactory;
    private final IExecutableFactory executableFactory;
    private final ILoggableFactory loggableFactory;
    private final ICommandFailedStatRepository execStatRepository;

    @Override
    public ICommand apply(ICommand failedCommand, Throwable throwable) {
        long failedCount = execStatRepository.getFailedCountByCommand(failedCommand);

        ICommand commandToSubmit = Optional.of(failedCount)
                .filter(count -> count < maxRetry)
                .map(count -> {
                    execStatRepository.incrementFailed(failedCommand);
                    IRetryable retryable = retryableFactory.create(failedCommand);
                    return commandFactory.createRetryCommand(retryable);
                })
                .orElseGet(() -> {
                    Logger logger = LoggerFactory.getLogger(failedCommand.getClass());
                    ILoggable loggable = loggableFactory.create(logger, throwable);
                    return commandFactory.createWriteLogCommand(loggable);
                });

        IExecutable executable = executableFactory.create(executor, commandToSubmit);
        return commandFactory.createSubmitCommand(executable);
    }

}

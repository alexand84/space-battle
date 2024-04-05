package me.alexand.spacebattle.service.exception.handlers;

import me.alexand.spacebattle.repository.ICommandFailedStatRepository;
import me.alexand.spacebattle.service.*;
import me.alexand.spacebattle.service.exceptions.handlers.RetryHandler;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import me.alexand.spacebattle.service.factory.IExecutableFactory;
import me.alexand.spacebattle.service.factory.ILoggableFactory;
import me.alexand.spacebattle.service.factory.IRetryableFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetryHandlerTests {

    @Test
    void shouldSuccessFirstRetryFailedCommand(@Mock ICommandsExecutor executor,
                                              @Mock ICommandFactory commandFactory,
                                              @Mock IRetryableFactory retryableFactory,
                                              @Mock ILoggableFactory loggableFactory,
                                              @Mock IExecutableFactory executableFactory,
                                              @Mock ICommandFailedStatRepository repository,
                                              @Mock IRetryable retryable,
                                              @Mock ICommand submitCommand,
                                              @Mock ICommand retryCommand,
                                              @Mock IExecutable executable,
                                              @Mock ICommand failedCommand) {
        long failedCount = 0;
        long maxRetry = 1;
        RuntimeException exception = new RuntimeException("error");

        when(repository.getFailedCountByCommand(failedCommand)).thenReturn(failedCount);
        when(retryableFactory.create(failedCommand)).thenReturn(retryable);
        when(commandFactory.createRetryCommand(retryable)).thenReturn(retryCommand);
        when(executableFactory.create(executor, retryCommand)).thenReturn(executable);
        when(commandFactory.createSubmitCommand(executable)).thenReturn(submitCommand);

        RetryHandler handler = new RetryHandler(executor, maxRetry, commandFactory, retryableFactory,
                executableFactory, loggableFactory, repository);
        ICommand actualCommand = handler.apply(failedCommand, exception);

        assertThat(actualCommand).isEqualTo(submitCommand);
        verify(repository, times(1)).getFailedCountByCommand(eq(failedCommand));
        verify(repository, times(1)).incrementFailed(eq(failedCommand));
        verify(retryableFactory, times(1)).create(eq(failedCommand));
        verify(commandFactory, times(1)).createRetryCommand(eq(retryable));
        verify(executableFactory, times(1)).create(eq(executor), eq(retryCommand));
        verify(commandFactory, times(1)).createSubmitCommand(eq(executable));
        verifyNoMoreInteractions(repository, retryableFactory, executableFactory, commandFactory);
    }

    @Test
    void shouldSuccessSecondRetryFailedCommand(@Mock ICommandsExecutor executor,
                                               @Mock ICommandFactory commandFactory,
                                               @Mock IRetryableFactory retryableFactory,
                                               @Mock ILoggableFactory loggableFactory,
                                               @Mock IExecutableFactory executableFactory,
                                               @Mock ICommandFailedStatRepository repository,
                                               @Mock IRetryable retryable,
                                               @Mock ICommand submitCommand,
                                               @Mock ICommand retryCommand,
                                               @Mock IExecutable executable,
                                               @Mock ICommand failedCommand) {
        long failedCount = 1;
        long maxRetry = 2;
        RuntimeException exception = new RuntimeException("error");

        when(repository.getFailedCountByCommand(failedCommand)).thenReturn(failedCount);
        when(retryableFactory.create(failedCommand)).thenReturn(retryable);
        when(commandFactory.createRetryCommand(retryable)).thenReturn(retryCommand);
        when(executableFactory.create(executor, retryCommand)).thenReturn(executable);
        when(commandFactory.createSubmitCommand(executable)).thenReturn(submitCommand);

        RetryHandler handler = new RetryHandler(executor, maxRetry, commandFactory, retryableFactory,
                executableFactory, loggableFactory, repository);
        ICommand actualCommand = handler.apply(failedCommand, exception);

        assertThat(actualCommand).isEqualTo(submitCommand);
        verify(repository, times(1)).getFailedCountByCommand(eq(failedCommand));
        verify(repository, times(1)).incrementFailed(eq(failedCommand));
        verify(retryableFactory, times(1)).create(eq(failedCommand));
        verify(commandFactory, times(1)).createRetryCommand(eq(retryable));
        verify(executableFactory, times(1)).create(eq(executor), eq(retryCommand));
        verify(commandFactory, times(1)).createSubmitCommand(eq(executable));
        verifyNoMoreInteractions(repository, retryableFactory, executableFactory, commandFactory);
    }

    @Test
    void shouldSuccessWriteLogFailedCommandDueToMaxRetryExceeded(@Mock Logger logger,
                                                                 @Mock ICommandsExecutor executor,
                                                                 @Mock ICommandFactory commandFactory,
                                                                 @Mock IRetryableFactory retryableFactory,
                                                                 @Mock ILoggableFactory loggableFactory,
                                                                 @Mock IExecutableFactory executableFactory,
                                                                 @Mock ICommandFailedStatRepository repository,
                                                                 @Mock ILoggable loggable,
                                                                 @Mock ICommand submitCommand,
                                                                 @Mock ICommand writeLogCommand,
                                                                 @Mock IExecutable executable,
                                                                 @Mock ICommand failedCommand) {
        try (MockedStatic<LoggerFactory> factory = Mockito.mockStatic(LoggerFactory.class)) {
            long failedCount = 2;
            long maxRetry = 2;
            RuntimeException exception = new RuntimeException("error");

            factory.when(() -> LoggerFactory.getLogger(failedCommand.getClass())).thenReturn(logger);
            when(repository.getFailedCountByCommand(failedCommand)).thenReturn(failedCount);
            when(loggableFactory.create(logger, exception)).thenReturn(loggable);
            when(commandFactory.createWriteLogCommand(loggable)).thenReturn(writeLogCommand);
            when(executableFactory.create(executor, writeLogCommand)).thenReturn(executable);
            when(commandFactory.createSubmitCommand(executable)).thenReturn(submitCommand);

            RetryHandler handler = new RetryHandler(executor, maxRetry, commandFactory, retryableFactory,
                    executableFactory, loggableFactory, repository);
            ICommand actualCommand = handler.apply(failedCommand, exception);

            assertThat(actualCommand).isEqualTo(submitCommand);
            verify(repository, times(1)).getFailedCountByCommand(eq(failedCommand));
            verify(loggableFactory, times(1)).create(eq(logger), eq(exception));
            verify(commandFactory, times(1)).createWriteLogCommand(eq(loggable));
            verify(executableFactory, times(1)).create(eq(executor), eq(writeLogCommand));
            verify(commandFactory, times(1)).createSubmitCommand(eq(executable));
            verifyNoMoreInteractions(repository, loggableFactory, executableFactory, commandFactory);
        }
    }

}

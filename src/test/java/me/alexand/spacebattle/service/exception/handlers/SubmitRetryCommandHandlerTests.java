package me.alexand.spacebattle.service.exception.handlers;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ICommandsExecutor;
import me.alexand.spacebattle.service.IExecutable;
import me.alexand.spacebattle.service.IRetryable;
import me.alexand.spacebattle.service.exceptions.handlers.Handler;
import me.alexand.spacebattle.service.exceptions.handlers.SubmitRetryCommandHandler;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import me.alexand.spacebattle.service.factory.IExecutableFactory;
import me.alexand.spacebattle.service.factory.IRetryableFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubmitRetryCommandHandlerTests {

    @Test
    void shouldSuccessSubmitRetryCommand(@Mock ICommandsExecutor executor,
                                         @Mock IRetryableFactory retryableFactory,
                                         @Mock IRetryable retryable,
                                         @Mock ICommandFactory commandFactory,
                                         @Mock ICommand submitCommand,
                                         @Mock ICommand retryCommand,
                                         @Mock IExecutableFactory executableFactory,
                                         @Mock IExecutable executable,
                                         @Mock ICommand failedCommand) {
        RuntimeException exception = new RuntimeException("error");

        when(retryableFactory.create(failedCommand)).thenReturn(retryable);
        when(commandFactory.createRetryCommand(retryable)).thenReturn(retryCommand);
        when(executableFactory.create(executor, retryCommand)).thenReturn(executable);
        when(commandFactory.createSubmitCommand(executable)).thenReturn(submitCommand);

        Handler handler = new SubmitRetryCommandHandler(executor, retryableFactory, commandFactory, executableFactory);
        ICommand actualCommand = handler.apply(failedCommand, exception);

        assertThat(actualCommand).isEqualTo(submitCommand);
        verify(retryableFactory, times(1)).create(eq(failedCommand));
        verify(executableFactory, times(1)).create(eq(executor), eq(retryCommand));
        verify(commandFactory, times(1)).createRetryCommand(eq(retryable));
        verify(commandFactory, times(1)).createSubmitCommand(eq(executable));
        verifyNoMoreInteractions(retryableFactory, executableFactory, commandFactory);
    }

}

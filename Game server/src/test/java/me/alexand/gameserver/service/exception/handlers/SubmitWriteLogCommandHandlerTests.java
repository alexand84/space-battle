package me.alexand.gameserver.service.exception.handlers;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;
import me.alexand.gameserver.service.IExecutable;
import me.alexand.gameserver.service.ILoggable;
import me.alexand.gameserver.service.exceptions.handlers.Handler;
import me.alexand.gameserver.service.exceptions.handlers.SubmitWriteLogCommandHandler;
import me.alexand.gameserver.service.factory.ICommandFactory;
import me.alexand.gameserver.service.factory.IExecutableFactory;
import me.alexand.gameserver.service.factory.ILoggableFactory;
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
public class SubmitWriteLogCommandHandlerTests {

    @Test
    void shouldSuccessSubmitWriteLog(@Mock Logger logger,
                                     @Mock ICommandsExecutor executor,
                                     @Mock ILoggableFactory loggableFactory,
                                     @Mock ILoggable loggable,
                                     @Mock ICommandFactory commandFactory,
                                     @Mock ICommand submitCommand,
                                     @Mock ICommand writeLogCommand,
                                     @Mock IExecutableFactory executableFactory,
                                     @Mock IExecutable executable,
                                     @Mock ICommand failedCommand) {

        try (MockedStatic<LoggerFactory> factory = Mockito.mockStatic(LoggerFactory.class)) {
            factory.when(() -> LoggerFactory.getLogger(failedCommand.getClass())).thenReturn(logger);

            RuntimeException exception = new RuntimeException("error");

            when(loggableFactory.create(logger, exception)).thenReturn(loggable);
            when(commandFactory.createWriteLogCommand(loggable)).thenReturn(writeLogCommand);
            when(executableFactory.create(executor, writeLogCommand)).thenReturn(executable);
            when(commandFactory.createSubmitCommand(executable)).thenReturn(submitCommand);

            Handler handler = new SubmitWriteLogCommandHandler(executor, loggableFactory, commandFactory, executableFactory);
            ICommand actualCommand = handler.apply(failedCommand, exception);

            assertThat(actualCommand).isEqualTo(submitCommand);
            verify(loggableFactory, times(1)).create(eq(logger), eq(exception));
            verify(commandFactory, times(1)).createWriteLogCommand(eq(loggable));
            verify(executableFactory, times(1)).create(eq(executor), eq(writeLogCommand));
            verify(commandFactory, times(1)).createSubmitCommand(eq(executable));
            verifyNoMoreInteractions(loggableFactory, executableFactory, commandFactory);
        }
    }

}

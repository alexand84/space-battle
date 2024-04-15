package me.alexand.gameserver.service.executors;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;
import me.alexand.gameserver.service.exceptions.handlers.ExceptionHandlers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandExecutorTests {

    @Test
    void shouldSuccessSubmitCommand(@Mock ICommand command,
                                    @Mock BlockingQueue<ICommand> queue,
                                    @Mock ExceptionHandlers handler) {
        when(queue.add(command)).thenReturn(true);
        ICommandsExecutor executor = new CommandsExecutorImpl(queue, handler);
        executor.submit(command);
        verify(queue, times(1)).add(command);
        verifyNoMoreInteractions(queue, command, handler);
    }

    @Test
    void shouldSuccessExecuteCommand(@Mock ICommand command,
                                     @Mock BlockingQueue<ICommand> queue,
                                     @Mock ExceptionHandlers handler) throws Exception {
        when(queue.take())
                .thenReturn(command)
                .thenThrow(InterruptedException.class);
        doNothing().when(command).execute();

        ICommandsExecutor executor = new CommandsExecutorImpl(queue, handler);
        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(2)).take();
        verify(command, times(1)).execute();
        verifyNoMoreInteractions(queue, command);
    }

    @Test
    void shouldFailExecuteCommand(@Mock ICommand mainCommand,
                                  @Mock ICommand commandOnFail,
                                  @Mock BlockingQueue<ICommand> queue,
                                  @Mock ExceptionHandlers handler) throws Exception {
        when(queue.take())
                .thenReturn(mainCommand)
                .thenThrow(InterruptedException.class);
        RuntimeException exception = new RuntimeException();
        doThrow(exception).when(mainCommand).execute();
        doNothing().when(commandOnFail).execute();
        when(handler.handle(mainCommand, exception)).thenReturn(commandOnFail);

        ICommandsExecutor executor = new CommandsExecutorImpl(queue, handler);
        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(2)).take();
        verify(mainCommand, times(1)).execute();
        verify(commandOnFail, times(1)).execute();
        verify(handler, times(1)).handle(eq(mainCommand), eq(exception));
        verifyNoMoreInteractions(queue, mainCommand, commandOnFail, handler);
    }

}

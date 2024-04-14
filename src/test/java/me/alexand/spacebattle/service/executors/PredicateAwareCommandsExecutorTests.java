package me.alexand.spacebattle.service.executors;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IPredicate;
import me.alexand.spacebattle.service.IPredicateAwareCommandsExecutor;
import me.alexand.spacebattle.service.exceptions.handlers.ExceptionHandlers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PredicateAwareCommandsExecutorTests {

    @Test
    void shouldSuccessSubmitCommand(@Mock ICommand command,
                                    @Mock BlockingQueue<ICommand> queue,
                                    @Mock ExceptionHandlers handlers) {
        when(queue.add(command)).thenReturn(true);
        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        executor.submit(command);
        verify(queue, times(1)).add(command);
        verifyNoMoreInteractions(queue, command, handlers);
    }

    @Test
    void shouldCallPredicate(@Mock BlockingQueue<ICommand> queue,
                             @Mock ExceptionHandlers handlers,
                             @Mock IPredicate predicate) throws Exception {
        when(predicate.test()).thenReturn(true);
        when(queue.take()).thenThrow(InterruptedException.class);

        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers, predicate);
        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(1)).take();
        verify(predicate, times(1)).test();
        verifyNoMoreInteractions(queue, predicate);
    }

    @Test
    void shouldUpdatePredicate(@Mock BlockingQueue<ICommand> queue,
                               @Mock ExceptionHandlers handlers,
                               @Mock IPredicate predicate) throws Exception {
        when(predicate.test()).thenReturn(true);
        when(queue.take()).thenThrow(InterruptedException.class);

        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        executor.updatePredicate(predicate);

        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(1)).take();
        verify(predicate, times(1)).test();
        verifyNoMoreInteractions(queue, predicate);
    }

    @Test
    void shouldSuccessExecuteCommand(@Mock ICommand command,
                                     @Mock BlockingQueue<ICommand> queue,
                                     @Mock ExceptionHandlers handlers) throws Exception {
        when(queue.take())
                .thenReturn(command)
                .thenThrow(InterruptedException.class);
        doNothing().when(command).execute();

        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
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
                                  @Mock ExceptionHandlers handlers) throws Exception {
        when(queue.take())
                .thenReturn(mainCommand)
                .thenThrow(InterruptedException.class);
        RuntimeException exception = new RuntimeException();
        doThrow(exception).when(mainCommand).execute();
        doNothing().when(commandOnFail).execute();
        when(handlers.handle(mainCommand, exception)).thenReturn(commandOnFail);

        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(2)).take();
        verify(mainCommand, times(1)).execute();
        verify(commandOnFail, times(1)).execute();
        verify(handlers, times(1)).handle(eq(mainCommand), eq(exception));
        verifyNoMoreInteractions(queue, mainCommand, commandOnFail, handlers);
    }

    @Test
    void shouldFailExecuteCommandReturnedByExceptionHandlers(@Mock ICommand mainCommand,
                                                             @Mock ICommand commandOnFail,
                                                             @Mock BlockingQueue<ICommand> queue,
                                                             @Mock ExceptionHandlers handlers) throws Exception {
        when(queue.take())
                .thenReturn(mainCommand)
                .thenThrow(InterruptedException.class);
        RuntimeException mainCommandException = new RuntimeException();
        RuntimeException commandOnFailException = new RuntimeException();
        doThrow(mainCommandException).when(mainCommand).execute();
        doThrow(commandOnFailException).when(commandOnFail).execute();
        when(handlers.handle(mainCommand, mainCommandException)).thenReturn(commandOnFail);
        when(handlers.handle(commandOnFail, commandOnFailException)).thenReturn(() -> {
        });

        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> assertThat(t).isNull())
                .join();

        verify(queue, times(2)).take();
        verify(mainCommand, times(1)).execute();
        verify(commandOnFail, times(1)).execute();
        verify(handlers, times(1)).handle(eq(mainCommand), eq(mainCommandException));
        verify(handlers, times(1)).handle(eq(commandOnFail), eq(commandOnFailException));
        verifyNoMoreInteractions(queue, mainCommand, commandOnFail, handlers);
    }

}

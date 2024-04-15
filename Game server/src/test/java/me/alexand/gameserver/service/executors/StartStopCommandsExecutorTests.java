package me.alexand.gameserver.service.executors;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IPredicateAwareCommandsExecutor;
import me.alexand.gameserver.service.exceptions.handlers.ExceptionHandlers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StartStopCommandsExecutorTests {

    @Test
    void shouldSuccessStartAndExecuteCommand(@Mock ExceptionHandlers handlers) throws InterruptedException {
        BlockingQueue<ICommand> queue = new LinkedBlockingQueue<>();
        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        CountDownLatch latch = new CountDownLatch(1);
        executor.submit(latch::countDown);

        new StartCommandsExecutorCommand(executor).execute();

        latch.await();
        assertThat(queue.size()).isEqualTo(0);
        verifyNoInteractions(handlers);
    }

    @Test
    void shouldSuccessHardStop(@Mock ExceptionHandlers handlers,
                               @Mock ICommand firstCommand,
                               @Mock ICommand lastCommand) {
        BlockingQueue<ICommand> queue = new LinkedBlockingQueue<>();
        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        doNothing().when(firstCommand).execute();

        executor.submit(firstCommand);
        executor.submit(new HardStopCommandsExecutorCommand(executor));
        executor.submit(lastCommand);

        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> {
                    assertThat(t).isNull();
                    assertThat(queue.size()).isEqualTo(1);
                    verify(firstCommand, times(1)).execute();
                    verifyNoMoreInteractions(firstCommand);
                    verifyNoInteractions(handlers, lastCommand);
                })
                .join();
    }

    @Test
    void shouldSuccessSoftStop(@Mock ExceptionHandlers handlers,
                               @Mock ICommand firstCommand,
                               @Mock ICommand lastCommand) {
        BlockingQueue<ICommand> queue = new LinkedBlockingQueue<>();
        IPredicateAwareCommandsExecutor executor = new PredicateAwareCommandsExecutorImpl(queue, handlers);
        doNothing().when(firstCommand).execute();
        doNothing().when(lastCommand).execute();

        executor.submit(firstCommand);
        executor.submit(new SoftStopCommandsExecutorCommand(queue, executor));
        executor.submit(lastCommand);

        CompletableFuture.runAsync(executor::run)
                .whenComplete((r, t) -> {
                    assertThat(t).isNull();
                    assertThat(queue.size()).isEqualTo(0);
                    verify(firstCommand, times(1)).execute();
                    verify(lastCommand, times(1)).execute();
                    verifyNoMoreInteractions(firstCommand, lastCommand);
                    verifyNoInteractions(handlers);
                })
                .join();
    }

}

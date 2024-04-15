package me.alexand.gameserver.service.executors;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IPredicate;
import me.alexand.gameserver.service.IPredicateAwareCommandsExecutor;
import me.alexand.gameserver.service.exceptions.handlers.ExceptionHandlers;

import java.util.concurrent.BlockingQueue;

public class PredicateAwareCommandsExecutorImpl implements IPredicateAwareCommandsExecutor {
    private final BlockingQueue<ICommand> commandsQueue;
    private final ExceptionHandlers exceptionHandlers;
    private IPredicate predicate;
    private volatile boolean isRunning = false;

    public PredicateAwareCommandsExecutorImpl(BlockingQueue<ICommand> commandsQueue,
                                              ExceptionHandlers exceptionHandlers) {
        this(commandsQueue, exceptionHandlers, () -> true);
    }

    public PredicateAwareCommandsExecutorImpl(BlockingQueue<ICommand> commandsQueue,
                                              ExceptionHandlers exceptionHandlers,
                                              IPredicate predicate) {
        this.commandsQueue = commandsQueue;
        this.exceptionHandlers = exceptionHandlers;
        this.predicate = predicate;
    }

    @Override
    public void submit(ICommand command) {
        commandsQueue.add(command);
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning && predicate.test()) {
            try {
                ICommand command = commandsQueue.take();
                execute(command);
            } catch (InterruptedException e) {
                stop();
            }
        }
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public void updatePredicate(IPredicate predicate) {
        this.predicate = predicate;
    }

    private void execute(ICommand command) {
        try {
            command.execute();
        } catch (Throwable t) {
            execute(exceptionHandlers.handle(command, t));
        }
    }

}

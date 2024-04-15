package me.alexand.spacebattle.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IPredicateAwareCommandsExecutor;

import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class SoftStopCommandsExecutorCommand implements ICommand {

    private final BlockingQueue<ICommand> commandsQueue;
    private final IPredicateAwareCommandsExecutor commandsExecutor;

    @Override
    public void execute() {
        commandsExecutor.updatePredicate(() -> commandsQueue.size() > 0);
    }

}

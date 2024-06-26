package me.alexand.gameserver.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IPredicateAwareCommandsExecutor;

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

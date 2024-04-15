package me.alexand.gameserver.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IPredicateAwareCommandsExecutor;

@RequiredArgsConstructor
public class StartCommandsExecutorCommand implements ICommand {

    private final IPredicateAwareCommandsExecutor commandsExecutor;

    @Override
    public void execute() {
        new Thread(commandsExecutor::run).start();
    }

}

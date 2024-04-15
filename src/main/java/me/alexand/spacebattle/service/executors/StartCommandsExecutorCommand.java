package me.alexand.spacebattle.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IPredicateAwareCommandsExecutor;

@RequiredArgsConstructor
public class StartCommandsExecutorCommand implements ICommand {

    private final IPredicateAwareCommandsExecutor commandsExecutor;

    @Override
    public void execute() {
        new Thread(commandsExecutor::run).start();
    }

}

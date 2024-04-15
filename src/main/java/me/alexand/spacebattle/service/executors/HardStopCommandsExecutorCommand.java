package me.alexand.spacebattle.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ICommandsExecutor;

@RequiredArgsConstructor
public class HardStopCommandsExecutorCommand implements ICommand {

    private final ICommandsExecutor commandsExecutor;

    @Override
    public void execute() {
        commandsExecutor.stop();
    }

}

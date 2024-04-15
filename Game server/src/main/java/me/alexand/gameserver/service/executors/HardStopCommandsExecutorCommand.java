package me.alexand.gameserver.service.executors;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;

@RequiredArgsConstructor
public class HardStopCommandsExecutorCommand implements ICommand {

    private final ICommandsExecutor commandsExecutor;

    @Override
    public void execute() {
        commandsExecutor.stop();
    }

}

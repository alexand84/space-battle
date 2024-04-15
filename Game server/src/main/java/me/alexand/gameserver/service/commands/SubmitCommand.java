package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IExecutable;

@RequiredArgsConstructor
public class SubmitCommand implements ICommand {

    private final IExecutable executable;

    @Override
    public void execute() {
        executable.getCommandExecutor().submit(executable.getCommand());
    }

}

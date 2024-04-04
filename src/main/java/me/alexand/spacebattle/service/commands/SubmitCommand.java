package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IExecutable;

@RequiredArgsConstructor
public class SubmitCommand implements ICommand {

    private final IExecutable executable;

    @Override
    public void execute() {
        executable.getCommandExecutor().submit(executable.getCommand());
    }

}

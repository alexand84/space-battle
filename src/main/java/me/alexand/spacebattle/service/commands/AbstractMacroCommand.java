package me.alexand.spacebattle.service.commands;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.exceptions.CommandException;

import java.util.List;

public abstract class AbstractMacroCommand implements ICommand {

    protected abstract List<ICommand> getCommands();

    @Override
    public void execute() {
        try {
            getCommands().forEach(ICommand::execute);
        } catch (Exception e) {
            throw new CommandException(getClass().getSimpleName(), e);
        }
    }

}

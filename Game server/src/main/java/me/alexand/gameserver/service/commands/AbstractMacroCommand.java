package me.alexand.gameserver.service.commands;


import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.exceptions.CommandException;

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

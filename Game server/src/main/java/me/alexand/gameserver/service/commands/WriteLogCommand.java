package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ILoggable;

@RequiredArgsConstructor
public class WriteLogCommand implements ICommand {

    private final ILoggable loggable;

    @Override
    public void execute() {
        loggable.getLogger().error("", loggable.getThrowable());
    }

}

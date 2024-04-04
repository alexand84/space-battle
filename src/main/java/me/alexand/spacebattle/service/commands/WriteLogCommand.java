package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.ILoggable;

@RequiredArgsConstructor
public class WriteLogCommand implements ICommand {

    private final ILoggable loggable;

    @Override
    public void execute() {
        loggable.getLogger().error("", loggable.getThrowable());
    }

}

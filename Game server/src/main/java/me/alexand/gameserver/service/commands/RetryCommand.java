package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IRetryable;

@RequiredArgsConstructor
public class RetryCommand implements ICommand {

    private final IRetryable retryable;

    @Override
    public void execute() {
        retryable.getCommand().execute();
    }

}

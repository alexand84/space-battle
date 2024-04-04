package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IRetryable;

@RequiredArgsConstructor
public class RetryCommand implements ICommand {

    private final IRetryable retryable;

    @Override
    public void execute() {
        retryable.getCommand().execute();
    }

}

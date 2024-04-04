package me.alexand.spacebattle.service.factory;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IExecutable;
import me.alexand.spacebattle.service.ILoggable;
import me.alexand.spacebattle.service.IRetryable;

public interface ICommandFactory {

    ICommand createWriteLogCommand(ILoggable loggable);

    ICommand createSubmitCommand(IExecutable executable);

    ICommand createRetryCommand(IRetryable retryable);

}

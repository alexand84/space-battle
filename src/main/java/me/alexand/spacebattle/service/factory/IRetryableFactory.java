package me.alexand.spacebattle.service.factory;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IRetryable;

public interface IRetryableFactory {

    IRetryable create(ICommand command);

}

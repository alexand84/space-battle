package me.alexand.gameserver.service.factory;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IRetryable;

public interface IRetryableFactory {

    IRetryable create(ICommand command);

}

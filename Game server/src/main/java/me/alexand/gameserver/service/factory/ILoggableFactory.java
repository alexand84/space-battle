package me.alexand.gameserver.service.factory;

import me.alexand.gameserver.service.ILoggable;
import org.slf4j.Logger;

public interface ILoggableFactory {

    ILoggable create(Logger logger, Throwable throwable);

}

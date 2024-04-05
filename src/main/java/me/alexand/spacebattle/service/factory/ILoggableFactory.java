package me.alexand.spacebattle.service.factory;

import me.alexand.spacebattle.service.ILoggable;
import org.slf4j.Logger;

public interface ILoggableFactory {

    ILoggable create(Logger logger, Throwable throwable);

}

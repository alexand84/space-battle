package me.alexand.spacebattle.service;

import org.slf4j.Logger;

public interface ILoggable {

    Logger getLogger();

    Throwable getThrowable();

}

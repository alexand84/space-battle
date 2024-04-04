package me.alexand.spacebattle.repository;

import me.alexand.spacebattle.service.ICommand;

public interface ICommandFailedStatRepository {

    long getFailedCountByCommand(ICommand command);

    void incrementFailed(ICommand command);

}

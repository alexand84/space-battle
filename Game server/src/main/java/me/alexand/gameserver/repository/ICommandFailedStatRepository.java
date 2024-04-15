package me.alexand.gameserver.repository;


import me.alexand.gameserver.service.ICommand;

public interface ICommandFailedStatRepository {

    long getFailedCountByCommand(ICommand command);

    void incrementFailed(ICommand command);

}

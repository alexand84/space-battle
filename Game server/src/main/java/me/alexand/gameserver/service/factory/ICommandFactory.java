package me.alexand.gameserver.service.factory;

import me.alexand.gameserver.service.*;

public interface ICommandFactory {

    ICommand createWriteLogCommand(ILoggable loggable);

    ICommand createSubmitCommand(IExecutable executable);

    ICommand createRetryCommand(IRetryable retryable);

    ICommand createCheckFuelCommand(IFuelPowered fuelPowered);

    ICommand createMoveCommand(IMovable movable);

    ICommand createRotateCommand(IRotatable rotatable);

    ICommand createBurnFuelCommand(IFuelPowered fuelPowered);

    ICommand createChangeVelocityCommand(IMovableAndRotatable movableAndRotatable);

}

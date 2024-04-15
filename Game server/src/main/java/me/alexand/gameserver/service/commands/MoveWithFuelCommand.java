package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IFuelPowered;
import me.alexand.gameserver.service.IMovable;
import me.alexand.gameserver.service.factory.ICommandFactory;

import java.util.List;

@RequiredArgsConstructor
public class MoveWithFuelCommand extends AbstractMacroCommand {

    private final ICommandFactory commandFactory;
    private final IFuelPowered fuelPowered;
    private final IMovable movable;

    @Override
    protected List<ICommand> getCommands() {
        return List.of(
                commandFactory.createCheckFuelCommand(fuelPowered),
                commandFactory.createMoveCommand(movable),
                commandFactory.createBurnFuelCommand(fuelPowered)
        );
    }

}

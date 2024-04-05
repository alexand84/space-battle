package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IFuelPowered;
import me.alexand.spacebattle.service.IMovable;
import me.alexand.spacebattle.service.factory.ICommandFactory;

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

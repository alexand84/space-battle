package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IMovableAndRotatable;
import me.alexand.spacebattle.service.factory.ICommandFactory;

import java.util.List;

@RequiredArgsConstructor
public class RotateAndChangeVelocityCommand extends AbstractMacroCommand {

    private final ICommandFactory commandFactory;
    private final IMovableAndRotatable movableAndRotatable;

    @Override
    protected List<ICommand> getCommands() {
        return List.of(
                commandFactory.createRotateCommand(movableAndRotatable),
                commandFactory.createChangeVelocityCommand(movableAndRotatable)
        );
    }

}

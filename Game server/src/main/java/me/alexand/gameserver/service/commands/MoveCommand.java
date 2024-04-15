package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.model.Vector;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IMovable;

@RequiredArgsConstructor
public class MoveCommand implements ICommand {

    private final IMovable movableObject;

    @Override
    public void execute() {
        Vector currentPosition = movableObject.getPosition();
        Vector velocity = movableObject.getVelocity();
        movableObject.setPosition(currentPosition.add(velocity));
    }

}

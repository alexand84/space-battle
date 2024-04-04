package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.model.Vector;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IMovable;

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

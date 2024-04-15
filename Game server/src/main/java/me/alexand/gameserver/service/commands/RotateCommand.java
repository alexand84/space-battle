package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IRotatable;

@RequiredArgsConstructor
public class RotateCommand implements ICommand {

    private final IRotatable rotatableObject;

    @Override
    public void execute() {
        int direction = rotatableObject.getDirection();
        int velocity = rotatableObject.getAngularVelocity();
        int directionsNumber = rotatableObject.getDirectionsNumber();
        rotatableObject.setDirection((direction + velocity) % directionsNumber);
    }

}

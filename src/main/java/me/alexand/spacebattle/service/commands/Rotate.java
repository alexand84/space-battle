package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IRotatable;

@RequiredArgsConstructor
public class Rotate implements ICommand {

    private final IRotatable rotatableObject;

    @Override
    public void execute() {
        int direction = rotatableObject.getDirection();
        int velocity = rotatableObject.getAngularVelocity();
        int directionsNumber = rotatableObject.getDirectionsNumber();
        rotatableObject.setDirection((direction + velocity) % directionsNumber);
    }

}

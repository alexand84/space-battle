package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.model.Vector;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IMovableAndRotatable;

@RequiredArgsConstructor
public class ChangeVelocityCommand implements ICommand {

    private final IMovableAndRotatable object;

    @Override
    public void execute() {
        Vector currentVelocity = object.getVelocity();
        int x = currentVelocity.getX();
        int y = currentVelocity.getY();
        double angle = Math.toRadians(object.getDirection());

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        int newX = (int) Math.round(x * cos - y * sin);
        int newY = (int) Math.round(x * sin + y * cos);

        object.setVelocity(
                Vector.builder()
                        .x(newX)
                        .y(newY)
                        .build()
        );
    }
}

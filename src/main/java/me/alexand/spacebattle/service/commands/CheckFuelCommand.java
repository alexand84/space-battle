package me.alexand.spacebattle.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IFuelPowered;
import me.alexand.spacebattle.service.exceptions.InsufficientFuelException;

@RequiredArgsConstructor
public class CheckFuelCommand implements ICommand {
    private final IFuelPowered fuelPoweredObject;

    @Override
    public void execute() {
        long currentFuelLevel = fuelPoweredObject.getFuelLevel();
        long fuelConsumptionRate = fuelPoweredObject.getFuelConsumptionRate();

        if ((currentFuelLevel - fuelConsumptionRate) < 0) {
            String message = String.format(
                    "Not enough fuel (%d) with current consumption (%d)",
                    currentFuelLevel,
                    fuelConsumptionRate
            );
            throw new InsufficientFuelException(message);
        }
    }
}

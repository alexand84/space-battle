package me.alexand.gameserver.service.commands;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IFuelPowered;

@RequiredArgsConstructor
public class BurnFuelCommand implements ICommand {
    private final IFuelPowered fuelPoweredObject;

    @Override
    public void execute() {
        long currentFuelLevel = fuelPoweredObject.getFuelLevel();
        long fuelConsumptionRate = fuelPoweredObject.getFuelConsumptionRate();
        fuelPoweredObject.setFuelLevel(currentFuelLevel - fuelConsumptionRate);
    }

}

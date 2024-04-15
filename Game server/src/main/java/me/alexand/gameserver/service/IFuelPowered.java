package me.alexand.gameserver.service;

public interface IFuelPowered {

    long getFuelLevel();

    long getFuelConsumptionRate();

    void setFuelLevel(long value);

}

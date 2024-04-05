package me.alexand.spacebattle.service;

public interface IFuelPowered {

    long getFuelLevel();

    long getFuelConsumptionRate();

    void setFuelLevel(long value);

}

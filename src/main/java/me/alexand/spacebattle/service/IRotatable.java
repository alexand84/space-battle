package me.alexand.spacebattle.service;

public interface IRotatable {

    int getDirection();

    int getAngularVelocity();

    void setDirection(int direction);

    int getDirectionsNumber();

}

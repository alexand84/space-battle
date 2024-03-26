package me.alexand.spacebattle.service;

import me.alexand.spacebattle.model.Vector;

public interface IMovable {

    Vector getPosition();

    void setPosition(Vector position);

    Vector getVelocity();

}

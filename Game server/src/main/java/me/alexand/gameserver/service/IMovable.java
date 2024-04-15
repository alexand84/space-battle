package me.alexand.gameserver.service;


import me.alexand.gameserver.model.Vector;

public interface IMovable {

    Vector getPosition();

    void setPosition(Vector position);

    Vector getVelocity();

}

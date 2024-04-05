package me.alexand.spacebattle.service;

import me.alexand.spacebattle.model.Vector;

public interface IMovableAndRotatable extends IMovable, IRotatable {

    void setVelocity(Vector velocity);

}

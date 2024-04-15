package me.alexand.gameserver.service;

import me.alexand.gameserver.model.Vector;

public interface IMovableAndRotatable extends IMovable, IRotatable {

    void setVelocity(Vector velocity);

}

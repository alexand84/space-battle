package me.alexand.spacebattle.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vector {
    private final int x;
    private final int y;

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

}

package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public class Block extends Collidable {
    public Block(double x, double y) {
        super(x, y);
    }

    @Override
    public boolean collides(Position playerPosition) {
        return false;
    }

    public boolean topCollision(Position currentPlayerPosition, Position previousPlayerPosition) {
        return false;
    }
}
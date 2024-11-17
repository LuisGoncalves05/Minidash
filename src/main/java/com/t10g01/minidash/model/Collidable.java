package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public abstract class Collidable {
    private Position position;

    public Collidable(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public abstract boolean collides(Player player);
}

package com.t10g01.minidash.model;

import com.t10g01.minidash.view.CollidableVisitor;

public abstract class Collidable {
    private final Position position;

    public Collidable(double x, double y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public abstract boolean collides(Position position);

    public abstract void accept(CollidableVisitor visitor);
}

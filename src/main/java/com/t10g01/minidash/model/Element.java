package com.t10g01.minidash.model;

import java.io.IOException;

public abstract class Element {
    private final Vector2D position;

    public Element(int x, int y) {
        this.position = new Vector2D(x, y);
    }

    public Vector2D getPosition() {
        return position;
    }

    public abstract void accept(ElementVisitor visitor) throws IOException;

    public abstract boolean collision(Player player);
}

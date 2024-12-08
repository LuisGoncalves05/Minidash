package com.t10g01.minidash.model;

public abstract class Element {
    private final Vector2D position;

    public Element(int x, int y) {
        this.position = new Vector2D(x, y);
    }

    public Vector2D getPosition() {
        return position;
    }

    public abstract void accept(ElementVisitor visitor);

    public abstract boolean collision(Player player);
}

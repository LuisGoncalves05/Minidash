package com.t10g01.minidash.model;

public class Platform extends Element {
    private final BoxCollider boxCollider;

    public Platform(int x, int y) {
        super(x, y);
        boxCollider = new BoxCollider(x, y + 0.75, 1, 0.25);
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitPlatform(this);
    }

    @Override
    public boolean collision(Player player) {
        return boxCollider.collision(player);
    }

    public boolean topCollision(Player player) {
        return boxCollider.topCollision(player);
    }
}

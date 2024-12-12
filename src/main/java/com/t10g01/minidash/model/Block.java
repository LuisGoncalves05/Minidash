package com.t10g01.minidash.model;

import java.io.IOException;

public class Block extends Element {
    private final BoxCollider collider;

    public Block(int x, int y) {
        super(x, y);
        collider = new BoxCollider(x, y, 1, 1);
    }

    @Override
    public boolean collision(Player player) {
        return collider.collision(player);
    }

    public boolean topCollision(Player player) {
        return collider.topCollision(player);
    }

    @Override
    public void accept(ElementVisitor visitor) throws IOException {
        visitor.visitBlock(this);
    }
}
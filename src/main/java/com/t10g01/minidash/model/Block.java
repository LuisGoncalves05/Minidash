package com.t10g01.minidash.model;

public class Block extends Collidable {
    public Block(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean collides(Player player) {
        return false;
    }
}

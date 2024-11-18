package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public class Block extends Collidable {
    public Block(double x, double y) {
        super(x, y);
    }

    @Override
    public boolean collides(Position playerPosition) {
        Position blockPosition = this.getPosition();
        boolean xOut = playerPosition.getX() > blockPosition.getX() + 1.0d || playerPosition.getX() + 1.0d < blockPosition.getX();
        boolean yOut = playerPosition.getY() > blockPosition.getY() + 1.0d || playerPosition.getY() + 1.0d < blockPosition.getY();
        return !(xOut || yOut);
    }

    public boolean topCollision(Position currentPlayerPosition, Position previousPlayerPosition) {
        Position blockPosition = this.getPosition();
        return this.collides(currentPlayerPosition) && previousPlayerPosition.getY() >= blockPosition.getY() + 1.0d;
    }
}
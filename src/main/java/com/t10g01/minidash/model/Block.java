package com.t10g01.minidash.model;

public class Block extends Element {
    public Block(double x, double y) {
        super(x, y);
    }

    public boolean collides(Vector2D playerPosition) {
        Vector2D blockPosition = this.getPosition();
        boolean xOut = playerPosition.getX() > blockPosition.getX() + 1.0d || playerPosition.getX() + 1.0d < blockPosition.getX();
        boolean yOut = playerPosition.getY() > blockPosition.getY() + 1.0d || playerPosition.getY() + 1.0d < blockPosition.getY();
        return !(xOut || yOut);
    }

    public boolean topCollision(Vector2D currentPlayerPosition, Vector2D previousPlayerPosition) {
        Vector2D blockPosition = this.getPosition();
        return this.collides(currentPlayerPosition) && previousPlayerPosition.getY() >= blockPosition.getY() + 1.0d;
    }

    public void accept(ElementVisitor visitor) {
        visitor.visitBlock(this);
    }
}
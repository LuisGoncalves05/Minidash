package com.t10g01.minidash.model;

public class LevelEnd extends Element {
    public LevelEnd(int x, int y) {
        super(x, y);
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitLevelEnd(this);
    }

    @Override
    public boolean collision(Player player) {
        return false;
    }
}

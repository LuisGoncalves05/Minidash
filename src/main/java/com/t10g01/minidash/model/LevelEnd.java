package com.t10g01.minidash.model;

import java.io.IOException;
import java.util.Objects;

public class LevelEnd extends Element {
    BoxCollider collider;
    public LevelEnd(int x, int y) {
        super(x, y);
        collider = new BoxCollider(x, y, 1, 1);
    }

    @Override
    public void accept(ElementVisitor visitor) throws IOException {
        visitor.visitLevelEnd(this);
    }

    @Override
    public boolean collision(Player player) {
        return collider.collision(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LevelEnd levelEnd)) return false;
        return getPosition().equals(levelEnd.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }
}

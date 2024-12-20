package com.t10g01.minidash.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoubleJump extends Element{
    private final BoxCollider boundingBox;
    private final List<BoxCollider> colliders;

    public DoubleJump(int x, int y) {
        super(x, y);
        boundingBox = new BoxCollider(x + 0.3, y + 0.3, 0.4, 0.4);
        colliders = new ArrayList<>();
        colliders.add(new BoxCollider(x + 0.35, y + 0.3, 0.3, 0.4));
        colliders.add(new BoxCollider(x + 0.3, y + 0.35, 0.4, 0.3));
    }

    @Override
    public boolean collision(Player player) {
        if (!boundingBox.collision(player)) return false;
        for (BoxCollider collider : colliders) if (collider.collision(player)) return true;
        return false;
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitDoubleJump(this);
    }

    // Constructor used for testing
    public DoubleJump(BoxCollider boundingBox, List<BoxCollider> colliders) {
        super(0, 0);
        this.boundingBox = boundingBox;
        this.colliders = colliders;
    }

    public BoxCollider getBoundingBox() {
        return boundingBox;
    }

    public List<BoxCollider> getColliders() {
        return colliders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleJump doubleJump)) return false;
        return doubleJump.getPosition().equals(getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }

}

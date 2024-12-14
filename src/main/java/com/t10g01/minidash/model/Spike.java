package com.t10g01.minidash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Spike extends Element {
    BoxCollider boundingBox;
    List<BoxCollider> colliders;

    public Spike(int x, int y) {
        super(x, y);
        boundingBox = new BoxCollider(x, y, 1, 0.5);

        colliders = new ArrayList<>();
        colliders.add(new BoxCollider(x + 0.1, y, 0.8, 0.1));
        colliders.add(new BoxCollider(x + 0.2, y + 0.1, 0.6, 0.1));
        colliders.add(new BoxCollider(x + 0.3, y + 0.2, 0.4, 0.1));
        colliders.add(new BoxCollider(x + 0.4, y + 0.3, 0.2, 0.1));
    }

    @Override
    public boolean collision(Player player) {
        if (!boundingBox.collision(player)) return false;
        for (BoxCollider collider : colliders) if (collider.collision(player)) return true;
        return false;
    }

    @Override
    public void accept(ElementVisitor visitor) throws IOException {
        visitor.visitSpike(this);
    }

    // Constructor used for testing
    public Spike(BoxCollider boundingBox, List<BoxCollider> colliders) {
        super(0, 0);
        this.boundingBox = boundingBox;
        this.colliders = colliders;
    }

    public List<BoxCollider> getColliders() {
        return colliders;
    }

    public BoxCollider getBoundingBox() {
        return boundingBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spike spike)) return false;
        return spike.getPosition().equals(getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}

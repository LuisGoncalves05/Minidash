package com.t10g01.minidash.model;

import java.util.ArrayList;
import java.util.List;

public class Spike extends Element {
    BoxCollider boundingBox;
    List<BoxCollider> colliders;

    public Spike(int x, int y) {
        super(x, y);

        boundingBox = new BoxCollider(x, y, 1, 0.5);

        colliders = new ArrayList<>();
        colliders.add(new BoxCollider(x + 0.1, y, 0.8, 0.1));
        colliders.add(new BoxCollider(x + 0.2, y, 0.6, 0.1));
        colliders.add(new BoxCollider(x + 0.3, y, 0.4, 0.1));
        colliders.add(new BoxCollider(x + 0.4, y, 0.2, 0.1));
    }

    public boolean collision(Player player) {
        if (!boundingBox.collision(player)) return false;
        for (BoxCollider collider : colliders) if (collider.collision(player)) return true;
        return false;
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitSpike(this);
    }
}

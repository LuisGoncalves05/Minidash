package com.t10g01.minidash.model;

import java.util.ArrayList;
import java.util.List;

public class Boost extends Element {
    BoxCollider boundingBox;
    List<BoxCollider> colliders;

    public Boost(int x, int y) {
        super(x, y);

        boundingBox = new BoxCollider(x + 0.1, y, 0.8, 0.2);

        colliders = new ArrayList<>();
        colliders.add(new BoxCollider(x + 0.1, y, 0.8, 0.1));
        colliders.add(new BoxCollider(x + 0.2, y + 0.1, 0.6, 0.1));
    }

    @Override
    public boolean collision(Player player) {
        if (!boundingBox.collision(player)) return false;
        for (BoxCollider collider : colliders) if (collider.collision(player)) return true;
        return false;
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitBoost(this);
    }

    // Constructor used for testing
    public Boost(BoxCollider boundingBox, List<BoxCollider> colliders) {
        super(0, 0);
        this.boundingBox = boundingBox;
        this.colliders = colliders;
    }
}

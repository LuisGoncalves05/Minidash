package com.t10g01.minidash.model;

import org.w3c.dom.html.HTMLLabelElement;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReversedSpike extends Element {
    BoxCollider boundingBox;
    List<BoxCollider> colliders;

    public ReversedSpike(int x, int y) {
        super(x, y);
        boundingBox = new BoxCollider(x, y + 0.5, 1, 0.5);

        colliders = new ArrayList<>();
        colliders.add(new BoxCollider(x + 0.1, y + 0.9, 0.8, 0.1));
        colliders.add(new BoxCollider(x + 0.2, y + 0.8, 0.6, 0.1));
        colliders.add(new BoxCollider(x + 0.3, y + 0.7, 0.4, 0.1));
        colliders.add(new BoxCollider(x + 0.4, y + 0.6, 0.2, 0.1));
    }

    @Override
    public boolean collision(Player player) {
        if (!boundingBox.collision(player)) return false;
        for (BoxCollider collider : colliders) if (collider.collision(player)) return true;
        return false;
    }

    @Override
    public void accept(ElementVisitor visitor) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        visitor.visitReversedSpike(this);
    }

    // Constructor used for testing
    public ReversedSpike(BoxCollider boundingBox, List<BoxCollider> colliders) {
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
        if (!(o instanceof ReversedSpike reversedSpike)) return false;
        return reversedSpike.getPosition().equals(getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}

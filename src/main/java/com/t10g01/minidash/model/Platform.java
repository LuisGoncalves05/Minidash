package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class Platform extends Element {
    private final BoxCollider boxCollider;

    public Platform(int x, int y) {
        super(x, y);
        boxCollider = new BoxCollider(x, y + 0.75, 1, 0.25);
    }

    @Override
    public void accept(ElementVisitor visitor) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        visitor.visitPlatform(this);
    }

    @Override
    public boolean collision(Player player) {
        return boxCollider.collision(player);
    }

    public boolean topCollision(Player player) {
        return boxCollider.topCollision(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform platform)) return false;
        return platform.getPosition().equals(getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}

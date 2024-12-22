package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class Block extends Element {
    private final BoxCollider collider;

    public Block(int x, int y) {
        super(x, y);
        collider = new BoxCollider(x, y, 1, 1);
    }

    @Override
    public boolean collision(Player player) {
        return collider.collision(player);
    }

    public boolean topCollision(Player player) {
        return collider.topCollision(player);
    }

    @Override
    public void accept(ElementVisitor visitor) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        visitor.visitBlock(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block block)) return false;
        return block.getPosition().equals(getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}
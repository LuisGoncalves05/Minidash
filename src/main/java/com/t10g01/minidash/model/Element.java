package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Element {
    private final Vector2D position;

    public Element(int x, int y) {
        this.position = new Vector2D(x, y);
    }

    public Vector2D getPosition() {
        return position;
    }

    public abstract void accept(ElementVisitor visitor) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    public abstract boolean collision(Player player);
}

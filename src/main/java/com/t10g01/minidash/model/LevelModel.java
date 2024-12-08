package com.t10g01.minidash.model;

import java.util.Comparator;
import java.util.List;

public class LevelModel {
    private final int height;
    private final int width;
    private final Player player;
    private final List<Element> elements;

    public LevelModel(int height, int width, Player player, List<Element> elements) {
        this.height = height;
        this.width = width;
        this.player = player;
        this.elements = elements;

        // elements should be a sorted list
        elements.sort(Comparator.comparingDouble(a -> a.getPosition().getX()));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Element> getElements() {
        return elements;
    }
}

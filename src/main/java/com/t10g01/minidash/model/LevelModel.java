package com.t10g01.minidash.model;

import java.util.List;

public class LevelModel {
    private int height;
    private int width;
    private Player player;
    private List<Element> elements;

    public LevelModel(int height, int width, Player player, List<Element> elements) {
        this.height = height;
        this.width = width;
        this.player = player;
        this.elements = elements;
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

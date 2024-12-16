package com.t10g01.minidash.model;

import java.util.List;

public class LevelModel {
    private final Player player;
    private final List<Element> elements;

    public LevelModel(List<Element> elements, int playerX, int playerY) {
        this.player = new Player(playerX, playerY);
        this.elements = elements;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Element> getElements() {
        return elements;
    }
}
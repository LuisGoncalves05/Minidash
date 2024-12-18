package com.t10g01.minidash.model;

import java.util.List;

public class LevelModel {
    private final Player player;
    private final List<Element> elements;
    private final int levelNumber;

    public LevelModel(List<Element> elements, int playerX, int playerY, int levelNumber) {
        this.player = new Player(playerX, playerY);
        this.elements = elements;
        this.levelNumber = levelNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Element> getElements() {
        return elements;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
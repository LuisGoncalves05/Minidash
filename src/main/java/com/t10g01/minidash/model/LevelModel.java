package com.t10g01.minidash.model;

import java.util.List;

public class LevelModel {
    private final Player player;
    private final List<Element> elements;

    public LevelModel(Player player, List<Element> elements) {
        this.player = player;
        this.elements = elements;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Element> getElements() {
        return elements;
    }
}
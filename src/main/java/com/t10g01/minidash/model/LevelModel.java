package com.t10g01.minidash.model;

import java.util.List;

public class LevelModel {
    private final int height;
    private final int width;
    private final Player player;
    private final List<Collidable> collidables;

    public LevelModel(int height, int width, Player player, List<Collidable> collidables) {
        this.height = height;
        this.width = width;
        this.player = player;
        this.collidables = collidables;
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

    public List<Collidable> getCollidables() {
        return collidables;
    }
}

package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public class Player {
    private Position position;
    private Position previousPosition;

    public Player(double x, double y) {
        Position position = new Position(x, y);
    }

    public void update() {

    }

    public void jump(double height, double time) {

    }
}

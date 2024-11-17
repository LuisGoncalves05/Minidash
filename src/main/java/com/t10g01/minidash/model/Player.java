package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public class Player {
    private Position position;
    private Position previousPosition = new Position(0, 0);

    public Player(double x, double y) {
        Position position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition = previousPosition;
    }
}

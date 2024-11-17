package com.t10g01.minidash.model;

import com.t10g01.minidash.shared.Position;

public class Player {
    private Position position;
    private Position previousPosition = new Position(0, 0);
    private Position speed = new Position(0, 0);
    private boolean grounded = false;
    public static final double defaultG = 9.8; // g used while falling
    double g = defaultG;

    public Player(double x, double y) {
        previousPosition = new Position(x, y);
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getSpeed() {
        return speed;
    }

    public boolean getGrounded() {
        return grounded;
    }

    public double getG() {
        return g;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition = previousPosition;
    }

    public void setSpeed(Position speed) {
        this.speed = speed;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void setG(double g) {
        this.g = g;
    }
}

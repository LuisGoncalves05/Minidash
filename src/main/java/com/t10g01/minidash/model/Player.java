package com.t10g01.minidash.model;

public class Player {
    private Vector2D position;
    private Vector2D previousPosition;

    private Vector2D speed = new Vector2D(9, 0);
    private double rotation = 0d;

    private boolean grounded = false;
    private boolean onDoubleJump = false;
    private boolean onBoost = false;

    public static final double defaultG = 80; // g used while falling
    double g = defaultG;

    public Player(double x, double y) {
        previousPosition = new Vector2D(x, y);
        position = new Vector2D(x, y);
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getPreviousPosition() {
        return previousPosition;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public boolean getGrounded() {
        return grounded;
    }

    public double getRotation() {
        return rotation;
    }

    public double getG() {
        return g;
    }

    public boolean getOnDoubleJump() {
        return onDoubleJump;
    }

    public boolean getOnBoost() {
        return onBoost;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setPreviousPosition(Vector2D previousPosition) {
        this.previousPosition = previousPosition;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    public void setGrounded(boolean grounded) {
        if (grounded) {
            getSpeed().setY(0);
        }
        this.grounded = grounded;
    }

    public boolean canJump() {
        return (grounded || onDoubleJump) && !onBoost;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setOnDoubleJump(boolean onDoubleJump) {
        this.onDoubleJump = onDoubleJump;
    }

    public void setOnBoost(boolean onBoost) {
        this.onBoost = onBoost;
    }
}

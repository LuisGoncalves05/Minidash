package com.t10g01.minidash.model;

import java.util.Objects;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void rotate(double angle) {

        double radians = Math.toRadians(angle);

        double new_x = x * Math.cos(radians) - y * Math.sin(radians);
        double new_y = x * Math.sin(radians) + y * Math.cos(radians);

        this.x = new_x;
        this.y = new_y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D position = (Vector2D) o;
        return Double.compare(x, position.x) == 0 && Double.compare(y, position.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

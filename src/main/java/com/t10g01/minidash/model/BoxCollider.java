package com.t10g01.minidash.model;

public class BoxCollider {
    private final Vector2D lowerLeft;
    private final Vector2D upperRight;

    public BoxCollider(int x, int y, double width, double height) {
        lowerLeft = new Vector2D(x, y);
        upperRight = new Vector2D(x + width, y + height);
    }

    public boolean collision(Player player) {
        Vector2D playerPosition = player.getPosition();

        if (playerPosition.getX() > upperRight.getX()) return false;
        if (playerPosition.getX() + 1 < lowerLeft.getX()) return false;
        if (playerPosition.getY() > upperRight.getY()) return false;
        if (playerPosition.getY() + 1 < lowerLeft.getY()) return false;

        return true;
    }

    public boolean topCollision(Player player) {
        if (!collision(player)) return false;
        return player.getPreviousPosition().getY() >= upperRight.getY();
    }
}

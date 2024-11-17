package com.t10g01.minidash.controller;

import com.t10g01.minidash.model.Player;
import com.t10g01.minidash.shared.Position;

public class PlayerController {
    private final Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(double deltaTime) {
        Position speed = player.getSpeed();
        Position newSpeed = new Position(speed.getX(), speed.getY() - player.getG() * deltaTime);

        Position position = player.getPosition();
        Position newPosition = new Position(position.getX() + newSpeed.getX() * deltaTime, position.getY() + newSpeed.getY() * deltaTime);

        player.setSpeed(newSpeed);
        player.setPosition(newPosition);

        player.setGrounded(false);
    }

    public void jump(double height, double time) {
        double speedX = player.getSpeed().getX();
        double speedY = 4 * height / time;
        player.setSpeed(new Position(speedX, speedY));

        player.setG(8 * height / (time * time));
    }

    public void setGrounded(double groundHeight) {
        player.setGrounded(true);
        double x = player.getPosition().getX();
        player.setPosition(new Position(x, groundHeight));
        player.setG(Player.defaultG);
    }
}

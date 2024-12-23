package com.t10g01.minidash.controller;

import com.t10g01.minidash.model.Player;
import com.t10g01.minidash.model.Vector2D;
import com.t10g01.minidash.utils.GameSettings;

public class PlayerController {
    private final Player player;
    private final double rotationSpeed;
    private final double playerSpeedX;

    public PlayerController(Player player, GameSettings settings) {
        this.player = player;
        this.rotationSpeed = settings.getRotationSpeed();
        this.playerSpeedX = settings.getPlayerSpeedX();
    }

    public void update(double deltaTime) {
        Vector2D speed = player.getSpeed();
        Vector2D newSpeed = new Vector2D(playerSpeedX, speed.getY() - player.getG() * deltaTime);

        Vector2D position = player.getPosition();
        player.setPreviousPosition(position);
        Vector2D newPosition = new Vector2D(position.getX() + newSpeed.getX() * deltaTime, position.getY() + newSpeed.getY() * deltaTime);

        player.setSpeed(newSpeed);
        player.setPosition(newPosition);

        if (player.getGrounded()) player.setRotation(0);
        else {
            double rotation = player.getRotation() + deltaTime * rotationSpeed;
            player.setRotation(rotation);
        }

        player.setGrounded(false);
    }

    public void jump(double height, double time) {
        if (!player.canJump()) return;

        double speedX = player.getSpeed().getX();
        double speedY = 4 * height / time;
        player.setSpeed(new Vector2D(speedX, speedY));
        player.setG(8 * height / (time * time));
    }

    public void groundPlayer(double groundHeight) {
        player.setGrounded(true);
        double x = player.getPosition().getX();
        player.setPosition(new Vector2D(x, groundHeight));
        player.setG(Player.defaultG);
    }
}

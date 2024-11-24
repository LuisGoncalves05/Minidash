package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.LevelAction;

import java.io.IOException;

public class LevelController extends Controller<LevelModel, LevelAction> implements CollidableVisitor {
    PlayerController playerController;

    public LevelController(LevelModel levelModel, Game game) {
       super(levelModel, game);
       playerController = new PlayerController(levelModel.getPlayer());
    }

    @Override
    public void step(LevelAction levelAction, double deltaTime) throws IOException {
        if (levelAction == LevelAction.EXIT) {
            game.setState(null);
            return;
        }

        if (levelAction == LevelAction.JUMP) playerController.jump(2, 0.2d);
        playerController.update(deltaTime);

        for (Collidable collidable : model.getCollidables()) {
            collidable.accept(this);
        }
    }

    @Override
    public void visitBlock(Block block) {
        Player player = model.getPlayer();
        Position playerPosition = player.getPosition();
        Position previousPlayerPosition = player.getPreviousPosition();

        if (block.topCollision(playerPosition, previousPlayerPosition)) {
            double height = block.getPosition().getY();
            playerController.setGrounded(height);
        } else if (block.collides(playerPosition)) {
            game.setState(null);
        }
    }

    // Constructor used for testing
    public LevelController(LevelModel levelModel, Game game, PlayerController playerController) {
        super(levelModel, game);
        this.playerController = playerController;
    }
}

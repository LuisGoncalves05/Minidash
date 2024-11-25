package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.LevelAction;

import java.io.IOException;

public class LevelController extends Controller<LevelModel, LevelAction> implements ElementVisitor {
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

        if (levelAction == LevelAction.JUMP) playerController.jump(3, 0.5);
        playerController.update(deltaTime);

        for (Element element : model.getElements()) {
            element.accept(this);
        }
    }

    @Override
    public void visitBlock(Block block) {
        Player player = model.getPlayer();

        if (block.topCollision(player)) {
            double height = block.getPosition().getY() + 1;
            playerController.setGrounded(height);
        } else if (block.collision(player)) {
            game.setState(null);
        }
    }

    @Override
    public void visitSpike(Spike spike) {
        if (spike.collision(model.getPlayer())) game.setState(null);
    }

    // Constructor used for testing
    public LevelController(LevelModel levelModel, Game game, PlayerController playerController) {
        super(levelModel, game);
        this.playerController = playerController;
    }
}

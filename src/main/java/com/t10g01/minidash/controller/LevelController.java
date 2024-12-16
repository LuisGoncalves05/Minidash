package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.state.LevelMenuState;
import com.t10g01.minidash.state.MainMenuState;
import com.t10g01.minidash.utils.LevelAction;

import java.io.IOException;
import java.util.List;

public class LevelController extends Controller<LevelModel, LevelAction> implements ElementVisitor {
    PlayerController playerController;

    // These pointers are used to handle collisions more efficiently: the LevelController only visits elements if there
    // is a chance of collision
    private int leftPointer = 0;
    private int rightPointer = 0;

    public LevelController(LevelModel levelModel, Game game) {
       super(levelModel, game);
       playerController = new PlayerController(levelModel.getPlayer(), game.getGameSettings());
    }

    @Override
    public void step(LevelAction levelAction, double deltaTime) throws IOException {
        if (levelAction == LevelAction.EXIT) {
            game.setState(new MainMenuState(game));
            return;
        }

        boolean inVoid = !playerController.update(deltaTime);
        if (inVoid) {
            game.restartLevel();
        }

        updatePointers();
        List<Element> elements = model.getElements();
        for (int i = leftPointer; i < rightPointer; i++) {
            elements.get(i).accept(this);
        }

        if (levelAction == LevelAction.JUMP) playerController.jump(3, 0.5);
    }

    @Override
    public void visitBlock(Block block) throws IOException {
        Player player = model.getPlayer();

        if (block.topCollision(player)) {
            double height = block.getPosition().getY() + 1;
            playerController.setGrounded(height);
        } else if (block.collision(player)) {
            game.restartLevel();
        }
    }

    @Override
    public void visitSpike(Spike spike) throws IOException {
        if (spike.collision(model.getPlayer())) game.restartLevel();
    }

    @Override
    public void visitReversedSpike(ReversedSpike reversedSpike) throws IOException {
        if (reversedSpike.collision(model.getPlayer())) game.restartLevel();
    }

    @Override
    public void visitPlatform(Platform platform) throws IOException {
        Player player = model.getPlayer();

        if (platform.topCollision(player)) {
            double height = platform.getPosition().getY() + 1;
            playerController.setGrounded(height);
        } else if (platform.collision(player)) {
            game.restartLevel();
        }
    }

    @Override
    public void visitBoost(Boost boost) {
        Player player = model.getPlayer();

        if (boost.collision(player)) {
            playerController.jump(5, 0.7);
            player.setGrounded(false);
        }
    }

    @Override
    public void visitDoubleJump(DoubleJump doubleJump) {
        Player player = model.getPlayer();
        if (doubleJump.collision(player)) {
            player.setGrounded(true);
        }
    }

    @Override
    public void visitLevelEnd(LevelEnd levelEnd) throws IOException {
        if (levelEnd.collision(model.getPlayer())) game.setState(new LevelMenuState(game));
    }

    // Constructor used for testing
    public LevelController(LevelModel levelModel, Game game, PlayerController playerController) {
        super(levelModel, game);
        this.playerController = playerController;
    }

    public void updatePointers() {
        double playerX = model.getPlayer().getPosition().getX();
        List<Element> elements = model.getElements();

        while (leftPointer < elements.size()) {
            double elementX = elements.get(leftPointer).getPosition().getX();
            if (elementX + 1 < playerX) leftPointer++;
            else break;
        }
        while (rightPointer < elements.size()) {
            double elementX = elements.get(rightPointer).getPosition().getX();
            if (elementX <= playerX + 1) rightPointer++;
            else break;
        }
    }

    public int getLeftPointer() {
        return leftPointer;
    }

    public int getRightPointer() {
        return rightPointer;
    }
}

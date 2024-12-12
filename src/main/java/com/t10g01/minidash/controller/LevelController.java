package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.state.MenuState;
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
       playerController = new PlayerController(levelModel.player(), game.getGameSettings());
    }

    @Override
    public void step(LevelAction levelAction, double deltaTime) throws IOException {
        if (levelAction == LevelAction.EXIT) {
            game.setState(new MenuState(game, game.getIoAdapter(), game.getGameSettings()));
            return;
        }

        if (levelAction == LevelAction.JUMP) playerController.jump(3, 0.5);
        playerController.update(deltaTime);

        updatePointers();
        List<Element> elements = model.elements();
        for (int i = leftPointer; i < rightPointer; i++) {
            elements.get(i).accept(this);
        }
    }

    @Override
    public void visitBlock(Block block) throws IOException {
        Player player = model.player();

        if (block.topCollision(player)) {
            double height = block.getPosition().getY() + 1;
            playerController.setGrounded(height);
        } else if (block.collision(player)) {
            game.restartLevel();
        }
    }

    @Override
    public void visitSpike(Spike spike) throws IOException {
        if (spike.collision(model.player())) game.restartLevel();
    }

    @Override
    public void visitPlatform(Platform platform) throws IOException {
        Player player = model.player();

        if (platform.topCollision(player)) {
            double height = platform.getPosition().getY() + 1;
            playerController.setGrounded(height);
        } else if (platform.collision(player)) {
            game.restartLevel();
        }
    }

    // Constructor used for testing
    public LevelController(LevelModel levelModel, Game game, PlayerController playerController) {
        super(levelModel, game);
        this.playerController = playerController;
    }

    public void updatePointers() {
        double playerX = model.player().getPosition().getX();
        List<Element> elements = model.elements();

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

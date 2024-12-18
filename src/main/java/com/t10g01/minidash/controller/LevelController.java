package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.sound.SoundPlayer;
import com.t10g01.minidash.sound.WAVPlayer;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.state.LevelCompleteState;
import com.t10g01.minidash.state.MainMenuState;
import com.t10g01.minidash.utils.LevelAction;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public class LevelController extends Controller<LevelModel, LevelAction> implements ElementVisitor {
    private final PlayerController playerController;
    private final SoundPlayer wavPlayer;

    // These pointers are used to handle collisions more efficiently: the LevelController only visits elements if there
    // is a chance of collision
    private int leftPointer = 0;
    private int rightPointer = 0;

    public LevelController(LevelModel levelModel, Game game) {
       super(levelModel, game);
       playerController = new PlayerController(levelModel.getPlayer(), game.getGameSettings());
       this.wavPlayer = new WAVPlayer(levelModel.getLevelNumber());
    }

    @Override
    public void step(LevelAction levelAction, double deltaTime) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        wavPlayer.playSound();

        if (levelAction == LevelAction.EXIT) {
            wavPlayer.stopSound();
            game.setState(new MainMenuState(game));
            return;
        }

        if (model.getPlayer().getPosition().getY() < 0) reset();

        playerController.update(deltaTime);

        updatePointers();
        model.getPlayer().setOnDoubleJump(false);
        model.getPlayer().setOnBoost(false);
        List<Element> elements = model.getElements();
        for (int i = leftPointer; i < rightPointer; i++) {
            elements.get(i).accept(this);
        }

        if (levelAction == LevelAction.JUMP) playerController.jump(3, 0.5);
    }
    
    public void reset() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        game.resetState();
        wavPlayer.stopSound();
    }

    @Override
    public void visitBlock(Block block) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Player player = model.getPlayer();

        if (block.topCollision(player)) {
            double height = block.getPosition().getY() + 1;
            playerController.groundPlayer(height);
        } else if (block.collision(player)) {
            reset();
        }
    }

    @Override
    public void visitSpike(Spike spike) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (spike.collision(model.getPlayer())) reset();
    }

    @Override
    public void visitReversedSpike(ReversedSpike reversedSpike) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (reversedSpike.collision(model.getPlayer())) reset();
    }

    @Override
    public void visitPlatform(Platform platform) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Player player = model.getPlayer();

        if (platform.topCollision(player)) {
            double height = platform.getPosition().getY() + 1;
            playerController.groundPlayer(height);
        } else if (platform.collision(player)) reset();
    }

    @Override
    public void visitBoost(Boost boost) {
        Player player = model.getPlayer();

        if (boost.collision(player)) {
            playerController.jump(5, 0.7);
            player.setGrounded(false);
            player.setOnBoost(true);
        }
    }

    @Override
    public void visitDoubleJump(DoubleJump doubleJump) {
        Player player = model.getPlayer();
        if (doubleJump.collision(player)) player.setOnDoubleJump(true);
    }

    @Override
    public void visitLevelEnd(LevelEnd levelEnd) throws IOException {
        if (levelEnd.collision(model.getPlayer())) {
            game.setState(new LevelCompleteState(game));
            wavPlayer.stopSound();
        }
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

    // Constructor used for testing
    public LevelController(LevelModel levelModel, Game game, PlayerController playerController, WAVPlayer wavPlayer) {
        super(levelModel, game);
        this.playerController = playerController;
        this.wavPlayer = wavPlayer;
    }

    public int getLeftPointer() {
        return leftPointer;
    }

    public int getRightPointer() {
        return rightPointer;
    }
}

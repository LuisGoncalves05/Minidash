package com.t10g01.minidash.view;

import com.t10g01.minidash.io.Output;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

import static java.lang.Math.ceil;

public class LevelView extends View<LevelModel> implements ElementVisitor {
    private final GameSettings gameSettings;
    private final double cameraWidth;
    private final double cameraXOffset;
    private final double cameraYOffset;

    private double cameraX;
    private double cameraY;

    // These pointers are used to make a more efficient rendering: the LevelView only attempts to render the elements
    // between them
    private int leftPointer = 0;
    private int rightPointer = 0;

    public LevelView(LevelModel model, Output output, GameSettings gameSettings) {
        super(model, output);
        this.gameSettings = gameSettings;
        this.cameraWidth = gameSettings.getCameraWidth();
        this.cameraXOffset = cameraWidth * 0.4;
        this.cameraYOffset = gameSettings.getCameraHeight() * gameSettings.getCameraCutoff();
        this.cameraX = model.getPlayer().getPosition().getX() - cameraXOffset;
    }

    @Override
    public void draw() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Player player = model.getPlayer();
        Vector2D position = player.getPosition();

        this.cameraX = position.getX() - cameraXOffset;
        this.cameraY = Math.max(position.getY() - cameraYOffset, 0);

        output.clear();

        drawPlayer(player);

        updatePointers();
        List<Element> elements = model.getElements();
        for (int i = leftPointer; i < rightPointer; i++) {
            elements.get(i).accept(this);
        }

        output.refresh();
    }

    @Override
    public void visitBlock(Block block) {
        Vector2D position = block.getPosition();

        double x = position.getX() - cameraX;
        double y = position.getY() - cameraY;

        int resolution = gameSettings.getResolution();
        int x_pixels = (int)(x * resolution);
        int y_pixels = (int)(y * resolution);

        output.drawRectangle(x_pixels, y_pixels, resolution, resolution, gameSettings.getBlockColor());
    }

    @Override
    public void visitSpike(Spike spike) {
        Vector2D position = spike.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int)((position.getX() - cameraX) * resolution);
        int y = (int)((position.getY() - cameraY) * resolution);

        for (int i = 0; i < resolution / 2; i++) {
            int left = x + i;
            int right = x + resolution - i;

            output.drawRectangle(x + i, y + i, right - left, 1, gameSettings.getSpikeColor());
        }
    }

    @Override
    public void visitReversedSpike(ReversedSpike reversedSpike) {
        Vector2D position = reversedSpike.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int)((position.getX() - cameraX) * resolution);
        int y = (int)((position.getY() - cameraY) * resolution);

        for (int i = 0; i < resolution / 2; i++) {
            int left = x + i;
            int right = x + resolution - i;

            output.drawRectangle(x + i, y + resolution - i - 1, right - left, 1, gameSettings.getSpikeColor());
        }
    }

    @Override
    public void visitPlatform(Platform platform) {
        Vector2D position = platform.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int) ((position.getX() - cameraX) * resolution);
        int y = (int) ((position.getY() + 0.75 - cameraY) * resolution);
        int height = (int) ceil(0.25 * resolution);

        output.drawRectangle(x, y, resolution, height, gameSettings.getPlatformColor());
    }

    @Override
    public void visitBoost(Boost boost) {
        Vector2D position = boost.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int)((position.getX() - cameraX) * resolution);
        int y = (int)((position.getY() - cameraY) * resolution);

        Color color  = gameSettings.getBoostColor();
        output.drawRectangle(x + 1, y, resolution - 2, 1, color);
        output.drawRectangle(x + 2, y + 1, resolution - 4, 1, color);
    }

    @Override
    public void visitLevelEnd(LevelEnd levelEnd) {}


    @Override
    public void visitDoubleJump(DoubleJump doubleJump) {
        Vector2D position = doubleJump.getPosition();

        int resolution = gameSettings.getResolution();
        int x = (int) (((position.getX() - cameraX) * resolution) + (resolution - 1) / 2.0);
        int y = (int) (((position.getY() - cameraY) * resolution) + (resolution - 1) / 2.0);

        output.drawCircle(x, y, resolution >> 2, gameSettings.getDoubleJumpColor());
    }

    public void drawPlayer(Player player) {
        Vector2D position = player.getPosition();
        int resolution = gameSettings.getResolution();

        double x_player = cameraXOffset * resolution;
        double y_player = Math.min(position.getY(), cameraYOffset) * resolution;
        int x_center = (int)(x_player + (double)resolution / 2);
        int y_center = (int)(y_player + (double)resolution / 2);

        double rotation = player.getRotation();
        Color playerColor = gameSettings.getPlayerColor();
        int biggestDeltaCenter = (int) (resolution * 0.70710678118);

        for (int i = -biggestDeltaCenter; i <= biggestDeltaCenter; i++) {
            for (int j = -biggestDeltaCenter; j <= biggestDeltaCenter; j++) {
                Vector2D centerToPixel = new Vector2D(i / (double) resolution, j / (double) resolution);
                centerToPixel.rotate(-rotation);

                if (Math.abs(centerToPixel.getX()) > 0.5) continue;
                if (Math.abs(centerToPixel.getY()) > 0.5) continue;

                output.drawPixel(x_center + i, y_center + j, playerColor);
            }
        }
    }

    public void updatePointers() {
        double cameraXRight = cameraX + cameraWidth;
        List<Element> elements = model.getElements();

        while (leftPointer < elements.size()) {
            double elementX = elements.get(leftPointer).getPosition().getX();
            if (elementX + 1 <= cameraX) leftPointer++;
            else break;
        }
        while (rightPointer < elements.size()) {
            double elementX = elements.get(rightPointer).getPosition().getX();
            if (elementX < cameraXRight) rightPointer++;
            else break;
        }
    }

    public int getLeftPointer() {
        return leftPointer;
    }

    public int getRightPointer() {
        return rightPointer;
    }

    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }
}

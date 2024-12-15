package com.t10g01.minidash.view;

import com.t10g01.minidash.model.*;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import java.io.IOException;
import java.util.List;

public class LevelView extends View<LevelModel> implements ElementVisitor {
    private final GameSettings gameSettings;
    private final double cameraWidth;
    private final double cameraXOffset;
    private final double cameraCutoff;
    private double cameraX;
    private double cameraY;

    // These pointers are used to make a more efficient rendering: the LevelView only attempts to render the elements
    // between them
    private int leftPointer = 0;
    private int rightPointer = 0;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        this.gameSettings = gameSettings;
        this.cameraWidth = gameSettings.getCameraWidth();
        this.cameraXOffset = cameraWidth * 0.4;
        this.cameraX = model.getPlayer().getPosition().getX() - cameraXOffset;
        this.cameraCutoff = gameSettings.getCameraHeight() * gameSettings.getCameraCutoff();
    }

    @Override
    public void draw() throws IOException {
        this.cameraX = model.getPlayer().getPosition().getX() - cameraXOffset;
        this.cameraY = Math.max(model.getPlayer().getPosition().getY() - cameraCutoff, 0);

        ioAdapter.clear();
        drawPlayer(model.getPlayer());

        updatePointers();
        List<Element> elements = model.getElements();
        for (int i = leftPointer; i < rightPointer; i++) {
            elements.get(i).accept(this);
        }

        ioAdapter.refresh();
    }

    @Override
    public void visitBlock(Block block) {

        Vector2D position = block.getPosition();
        int resolution = gameSettings.getResolution();

        double x = position.getX() - cameraX;
        double y = position.getY() - cameraY;

        int x_pixels = (int)(x * resolution);
        int y_pixels = (int)(y * resolution);

        ioAdapter.drawRectangle(x_pixels, y_pixels, resolution, resolution, gameSettings.getBlockColor());
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

            ioAdapter.drawRectangle(x + i, y + i, right - left, 1, gameSettings.getSpikeColor());
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

            ioAdapter.drawRectangle(x + i, y + resolution - i - 1, right - left, 1, gameSettings.getSpikeColor());
        }
    }

    @Override
    public void visitPlatform(Platform platform) {
        Vector2D position = platform.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int) ((position.getX() - cameraX) * resolution);
        int y = (int) ((position.getY() + 0.75 - cameraY) * resolution);
        int height = (int) (0.25 * resolution);

        ioAdapter.drawRectangle(x, y, resolution, height, gameSettings.getPlatformColor());
    }

    @Override
    public void visitBoost(Boost boost) {
        Vector2D position = boost.getPosition();
        int resolution = gameSettings.getResolution();

        int x = (int)((position.getX() - cameraX) * resolution);
        int y = (int)((position.getY() - cameraY) * resolution);

        ioAdapter.drawRectangle(x + 1, y, resolution - 2, 1, gameSettings.getBoostColor());
        ioAdapter.drawRectangle(x + 2, y + 1, resolution - 4, 1, gameSettings.getBoostColor());
    }

    @Override
    public void visitLevelEnd(LevelEnd levelEnd) {

    }


    public void drawPlayer(Player player) {
        int resolution = gameSettings.getResolution();

        Vector2D playerPosition = player.getPosition();

        double x_player = cameraXOffset * resolution;
        double y_player;
        if(model.getPlayer().getPosition().getY() <= cameraCutoff) {
            y_player = playerPosition.getY() * resolution;
        } else {
            y_player = cameraCutoff * resolution;
        }

        double rotation = player.getRotation();
        Color playerColor = gameSettings.getPlayerColor();

        double x_center = x_player + (double)resolution / 2;
        double y_center = y_player + (double)resolution / 2;

        for(int i = 0; i < resolution; i++) {
            for(int j = 0; j < resolution; j++) {

                Vector2D pixelPosition = new Vector2D(
                        x_player + i,
                        y_player + j
                );

                Vector2D centerToPixel = new Vector2D(
                        pixelPosition.getX() - x_center,
                        pixelPosition.getY() - y_center
                );

                centerToPixel.rotate(rotation);

                Vector2D finalPosition = new Vector2D(
                        x_center + centerToPixel.getX(),
                        y_center + centerToPixel.getY()
                );

                ioAdapter.drawPixel((int)finalPosition.getX(), (int)finalPosition.getY(), playerColor);
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

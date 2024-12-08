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
    private final double cameraOffset;
    private double cameraX;

    // These pointers are used to make a more efficient rendering: the LevelView only attempts to render the elements
    // between them
    private int leftPointer = 0;
    private int rightPointer = 0;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        this.gameSettings = gameSettings;
        this.cameraWidth = gameSettings.getCameraWidth();
        this.cameraOffset = cameraWidth * 0.4;
        this.cameraX = model.getPlayer().getPosition().getX() - cameraOffset;
    }

    @Override
    public void draw() throws IOException {
        this.cameraX = model.getPlayer().getPosition().getX() - cameraOffset;
        updatePointers();

        ioAdapter.clear();
        drawPlayer(model.getPlayer());

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
        double y = position.getY();

        int x_pixels = (int)(x * resolution);
        int y_pixels = (int)(y * resolution);

        if(x <= -1 || x >= gameSettings.getCameraWidth()) return;

        if (x < 0) {
            int blockWidth = resolution + x_pixels;
            ioAdapter.drawRectangle(0, y_pixels, blockWidth, resolution, gameSettings.getBlockColor());
        } else if(x > gameSettings.getCameraWidth() - 1) {
            int blockWidth = gameSettings.getCameraWidth() * resolution - x_pixels;
            ioAdapter.drawRectangle(x_pixels, y_pixels, blockWidth, resolution, gameSettings.getBlockColor());
        } else {
            ioAdapter.drawRectangle(x_pixels, y_pixels, resolution, resolution, gameSettings.getBlockColor());
        }
    }

    @Override
    public void visitSpike(Spike spike) {
        Vector2D position = spike.getPosition();
        int resolution = gameSettings.getResolution();
        int x = (int)((position.getX() - cameraX) * resolution);
        int y = (int)(position.getY() * resolution);

        if (x <= -resolution || x >= gameSettings.getCameraWidth() * resolution) return;

        for (int i = 0; i < resolution / 2; i++) {
            int left = Math.max(x + i, 0);
            int right = Math.min(x + resolution - i, gameSettings.getCameraWidth() * resolution);
            if (right < left) continue;

            ioAdapter.drawRectangle(left, y + i, right - left, 1, gameSettings.getSpikeColor());
        }
    }

    public void drawPlayer(Player player) {
        int resolution = gameSettings.getResolution();

        Vector2D playerPosition = player.getPosition();
        double x_player = cameraOffset * resolution;
        double y_player = playerPosition.getY() * resolution;

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

    private void updatePointers() {
        double cameraXRight = cameraX + cameraWidth * gameSettings.getResolution();
        List<Element> elements = model.getElements();

        while (leftPointer < elements.size()) {
            double elementX = elements.get(leftPointer).getPosition().getX();
            if (elementX + 1 < cameraX) leftPointer++;
            else break;
        }
        while (rightPointer < elements.size()) {
            double elementX = elements.get(rightPointer).getPosition().getX();
            if (elementX < cameraXRight) rightPointer         ++;
            else break;
        }
    }
}

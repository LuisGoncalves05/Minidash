package com.t10g01.minidash.view;

import com.t10g01.minidash.model.*;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import java.io.IOException;

public class LevelView extends View<LevelModel> implements ElementVisitor {

    private final GameSettings gameSettings;
    private final double cameraXOffset;
    private final double cameraY;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        int cameraWidth = gameSettings.getCameraWidth();
        this.gameSettings = gameSettings;
        this.cameraXOffset = cameraWidth * 0.4;
        this.cameraY = 0;
    }

    public void draw() throws IOException {
        ioAdapter.clear();
        drawPlayer(model.getPlayer());
        for(Element element : model.getElements()) {
            element.accept(this);
        }
        ioAdapter.refresh();
    }

    public void visitBlock(Block block) {
        Vector2D position = block.getPosition();
        int resolution = gameSettings.getResolution();
        double x = position.getX() - model.getPlayer().getPosition().getX() + cameraXOffset;
        double y = position.getY();
        // TODO change y positions to adjust to proper cameraY

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

    public void visitSpike(Spike spike) {
        Vector2D position = spike.getPosition();
        int resolution = gameSettings.getResolution();
        int x = (int)((spike.getPosition().getX() - model.getPlayer().getPosition().getX() + cameraXOffset) * resolution);
        int y = (int)(spike.getPosition().getY() * resolution);
        // TODO change y positions to adjust to proper cameraY

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
        double x_player = cameraXOffset * resolution;
        double y_player = (double) playerPosition.getY() * resolution;
        // TODO change y positions to adjust to proper cameraY

        double rotation = player.getRotation();
        Color playerColor = gameSettings.getPlayerColor();

        double x_center = x_player + (double)resolution / 2;
        double y_center = y_player + (double)resolution / 2;

        for(int i = 0; i < resolution; i++) {
            for(int j = 0; j < resolution; j++) {

                Vector2D pixelPosition = new Vector2D(
                        x_player + i,
                        y_player + j);

                Vector2D centerToPixel = new Vector2D(
                        pixelPosition.getX() - x_center,
                        pixelPosition.getY() - y_center);

                centerToPixel.rotate(rotation);

                Vector2D finalPosition = new Vector2D(
                        x_center + centerToPixel.getX(),
                        y_center + centerToPixel.getY()
                );

                ioAdapter.drawPixel((int)finalPosition.getX(), (int)finalPosition.getY(), playerColor);
            }
        }
    }

}

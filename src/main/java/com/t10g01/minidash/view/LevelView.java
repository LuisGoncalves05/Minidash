package com.t10g01.minidash.view;

import com.t10g01.minidash.model.*;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import java.io.IOException;

public class LevelView extends View<LevelModel> implements ElementVisitor {

    private final GameSettings gameSettings;
    private final double cameraXOffset;
    private final double cameraCutoff;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        this.gameSettings = gameSettings;
        this.cameraXOffset = gameSettings.getCameraWidth() * 0.4;
        this.cameraCutoff = gameSettings.getCameraHeight() * gameSettings.getCameraCutoff();
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

        double cameraYOffset = 0;
        if(model.getPlayer().getPosition().getY() > cameraCutoff) {
            cameraYOffset = model.getPlayer().getPosition().getY() - cameraCutoff;
        }

        Vector2D position = block.getPosition();
        int resolution = gameSettings.getResolution();
        double x = position.getX() - model.getPlayer().getPosition().getX() + cameraXOffset;
        double y = position.getY() - cameraYOffset;

        int x_pixels = (int)(x * resolution);
        int y_pixels = (int)(y * resolution);

        if(x <= -1 || x >= gameSettings.getCameraWidth()
            || y <= -1 || y >= gameSettings.getCameraHeight()) return;

        int blockWidth = gameSettings.getResolution();
        int blockHeight = gameSettings.getResolution();
        if (x < 0) {
            blockWidth = resolution + x_pixels;
            x_pixels = 0;
        } else if(x > gameSettings.getCameraWidth() - 1) {
            blockWidth = gameSettings.getCameraWidth() * resolution - x_pixels;
        }

        if(y < 0) {
            blockHeight = resolution + y_pixels;
            y_pixels = 0;
        } else if(y > gameSettings.getCameraHeight() - 1) {
            blockHeight = gameSettings.getCameraHeight() * resolution - y_pixels;
        }

        ioAdapter.drawRectangle(x_pixels, y_pixels, blockWidth, blockHeight, gameSettings.getBlockColor());
    }

    public void visitSpike(Spike spike) {

        double cameraYOffset = 0;
        if(model.getPlayer().getPosition().getY() > cameraCutoff) {
            cameraYOffset = model.getPlayer().getPosition().getY() - cameraCutoff;
        }

        Vector2D position = spike.getPosition();
        int resolution = gameSettings.getResolution();
        double x = position.getX() - model.getPlayer().getPosition().getX() + cameraXOffset;
        double y = position.getY() - cameraYOffset;

        int x_pixels = (int)(x * resolution);
        int y_pixels = (int)(y * resolution);

        if(x <= -1 || x >= gameSettings.getCameraWidth()
                || y <= -1 || y >= gameSettings.getCameraHeight()) return;

        for (int i = 0; i < resolution / 2; i++) {
            int left = Math.max(x_pixels + i, 0);
            int right = Math.min(x_pixels + resolution - i, gameSettings.getCameraWidth() * resolution);
            if (right < left) continue;

            ioAdapter.drawRectangle(left, y_pixels + i, right - left, 1, gameSettings.getSpikeColor());
        }
    }

    public void drawPlayer(Player player) {

        int resolution = gameSettings.getResolution();

        Vector2D playerPosition = player.getPosition();
        double x_player = cameraXOffset * resolution;
        double y_player;
        if(model.getPlayer().getPosition().getY() <= cameraCutoff) {
            y_player = (double) playerPosition.getY() * resolution;
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

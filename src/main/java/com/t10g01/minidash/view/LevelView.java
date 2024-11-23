package com.t10g01.minidash.view;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import java.io.IOException;

public class LevelView extends View<LevelModel> implements CollidableVisitor {

    private GameSettings gameSettings;
    private double cameraOffset;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        int cameraWidth = gameSettings.getCameraWidth();
        this.gameSettings = gameSettings;
        this.cameraOffset = cameraWidth * 0.4;
    }

    public void draw() throws IOException {
        ioAdapter.clear();
        drawPlayer(model.getPlayer());
        for(Collidable collidable: model.getCollidables()) {
            collidable.accept(this);
        }
        ioAdapter.refresh();
    }

    public void visitBlock(Block block) {
        Position position = block.getPosition();
        int resolution = gameSettings.getResolution();
        double x = position.getX() - model.getPlayer().getPosition().getX() + cameraOffset;
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

    public void drawPlayer(Player player) {
        Position position = player.getPosition();
        int resolution = gameSettings.getResolution();

        int x_pixels = (int)(cameraOffset * resolution);
        int y_pixels = (int)(position.getY() * resolution);

        ioAdapter.drawRectangle(x_pixels, y_pixels, resolution, resolution, gameSettings.getPlayerColor());
    }

}

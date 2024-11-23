package com.t10g01.minidash.view;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.Collidable;
import com.t10g01.minidash.model.Position;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.Block;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

public class LevelView extends View<LevelModel> implements CollidableVisitor {

    private GameSettings gameSettings;
    private double cameraOffset;

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        int cameraWidth = gameSettings.getCameraWidth();
        this.gameSettings = gameSettings;
        this.cameraOffset = cameraWidth * 0.4;
    }

    public void draw() {
        for(Collidable collidable: model.getCollidables()) {
            collidable.accept(this);
        }

        // TODO: draw player
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

}

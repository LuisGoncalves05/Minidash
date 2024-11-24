package com.t10g01.minidash.utils;

public class GameSettings {

    private final int resolution;

    private final int cameraWidth;
    private final int cameraHeight;

    private final Color backgroundColor;
    private final Color blockColor;
    private final Color playerColor;

    public GameSettings(int resolution, int cameraWidth, int cameraHeight) {
        this.resolution = resolution;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;

        backgroundColor = new Color("#ebebeb");
        blockColor = new Color("#307098");
        playerColor = new Color("#e87713");
    }

    public int getResolution() {
        return resolution;
    }

    public int getCameraWidth() {
        return cameraWidth;
    }

    public int getCameraHeight() {
        return cameraHeight;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }
}

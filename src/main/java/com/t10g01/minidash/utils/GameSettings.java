package com.t10g01.minidash.utils;

public class GameSettings {

    private final int resolution;

    private final int cameraWidth;
    private final int cameraHeight;

    private final Color menuOptionColor;
    private final Color selectedOptionColor;

    private final Color backgroundColor;
    private final Color blockColor;
    private final Color playerColor;
    private final Color spikeColor;

    private final double jumpTime;
    private final double rotationSpeed;

    public GameSettings(int resolution, int cameraWidth, int cameraHeight) {
        this.resolution = resolution;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;

        menuOptionColor = new Color("#645d3b");
        selectedOptionColor = new Color("#31331f");

        backgroundColor = new Color("#d5cdbc");
        blockColor = new Color("#645d3b");
        playerColor = new Color("#ba8b68");
        spikeColor = new Color("#31331f");

        jumpTime = 0.5;
        rotationSpeed = - 180d / jumpTime;
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

    public Color getMenuOptionColor() {
        return menuOptionColor;
    }

    public Color getSelectedOptionColor() {
        return selectedOptionColor;
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

    public Color getSpikeColor() {
        return spikeColor;
    }

    public double getJumpTime() {
        return jumpTime;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }
}

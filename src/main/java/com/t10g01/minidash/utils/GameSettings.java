package com.t10g01.minidash.utils;

public class GameSettings {

    private final int resolution;

    private final int cameraWidth;
    private final int cameraHeight;
    private final double cameraCutoff;

    private final Color menuOptionColor;
    private final Color selectedOptionColor;

    private final Color backgroundColor;
    private final Color blockColor;
    private final Color playerColor;
    private final Color spikeColor;
    private final Color platformColor;
    private final Color boostColor;
    private final Color doubleJumpColor;

    private final double jumpTime = 0.5;
    private final double rotationSpeed = -180d / jumpTime;

    public GameSettings() {
        this.resolution = 10;
        this.cameraWidth = 20;
        this.cameraHeight = 10;
        this.cameraCutoff = 0.75;

        menuOptionColor = new Color("#645d3b");
        selectedOptionColor = new Color("#31331f");

        backgroundColor = new Color("#e4d8c8");
        blockColor = new Color("#5a3e39");
        playerColor = new Color("#e07d45");
        spikeColor = new Color("#dd805d");
        platformColor = blockColor;
        boostColor = new Color("#d78b30");
        doubleJumpColor = new Color("#cc8899");
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

    public double getCameraCutoff() {
        return cameraCutoff;
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

    public Color getBoostColor() {
        return boostColor;
    }

    public Color getPlatformColor() {
        return platformColor;
    }

    public double getJumpTime() {
        return jumpTime;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public Color getDoubleJumpColor() { return doubleJumpColor;}
}

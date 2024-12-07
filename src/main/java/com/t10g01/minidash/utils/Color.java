package com.t10g01.minidash.utils;

public class Color {
    private int red;
    private int green;
    private int blue;

    public Color(String hexCode) {
        this.red = Integer.valueOf(hexCode.substring(1, 3), 16);
        this.green = Integer.valueOf(hexCode.substring(3, 5), 16);
        this.blue = Integer.valueOf(hexCode.substring(5, 7), 16);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}

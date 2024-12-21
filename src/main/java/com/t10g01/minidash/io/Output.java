package com.t10g01.minidash.io;

import com.t10g01.minidash.utils.Color;

import java.io.IOException;

public interface Output {
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
    void drawPixel(int x, int y, Color color);
    void drawRectangle(int x, int y, int width, int height, Color color);
    void drawCircle(int x, int y, int radius, Color color);
    int getScreenHeight();
    int getScreenWidth();
}

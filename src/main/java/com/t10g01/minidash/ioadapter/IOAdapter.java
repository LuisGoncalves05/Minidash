package com.t10g01.minidash.ioadapter;

import java.io.IOException;
import com.t10g01.minidash.utils.Color;

public interface IOAdapter {
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
    void drawPixel(int x, int y, Color color);
    void drawRectangle(int x, int y, int width, int height, Color color);
    boolean isPressed(char key);
}

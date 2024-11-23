package com.t10g01.minidash.ioadapter;

import java.io.IOException;
import com.t10g01.minidash.utils.Color;

public interface IOAdapter {
    public void clear();
    public void refresh() throws IOException;
    public void close() throws IOException;
    public void drawPixel(int x, int y, Color color);
    public void drawRectangle(int x, int y, int width, int height, Color color);
    public boolean isPressed(char key);
}

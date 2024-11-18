package com.t10g01.minidash.ioadapter;

import com.t10g01.minidash.utils.Color;

public interface IOAdapter {

    public void clear();

    public void refresh();

    public void drawPixel(int x, int y, Color color);

    public boolean isPressed(char key);

}

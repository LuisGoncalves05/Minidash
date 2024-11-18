package com.t10g01.minidash.view;

import com.t10g01.minidash.utils.Color;

import java.io.IOException;

public interface Output {

    void clear();

    void drawPixel(int x, int y, Color color);

    void refresh() throws IOException;

}

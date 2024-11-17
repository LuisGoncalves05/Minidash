package com.t10g01.minidash.view;


import com.t10g01.minidash.shared.Color;
import com.t10g01.minidash.shared.Position;

public interface Output {

    void clear();

    void drawPixel(Position position, Color color);

    void refresh();

}

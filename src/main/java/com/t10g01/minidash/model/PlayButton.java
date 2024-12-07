package com.t10g01.minidash.model;

public class PlayButton implements MenuOption {
    @Override
    public void accept(MenuOptionVisitor visitor) {
        visitor.visitPlayButton(this);
    }
}

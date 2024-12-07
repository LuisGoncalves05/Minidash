package com.t10g01.minidash.model;

public interface MenuOptionVisitor {
    void visitPlayButton(PlayButton playButton);
    void visitExitButton(ExitButton exitButton);
}

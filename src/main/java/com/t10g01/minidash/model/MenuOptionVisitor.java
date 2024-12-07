package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public interface MenuOptionVisitor {
    void visitPlayButton(PlayButton playButton) throws URISyntaxException, IOException;
    void visitExitButton(ExitButton exitButton) throws URISyntaxException, IOException;
}

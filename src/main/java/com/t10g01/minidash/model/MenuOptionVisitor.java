package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public interface MenuOptionVisitor {
    void visitLevelsButton(LevelsButton levelsButton) throws URISyntaxException, IOException;
    void visitExitButton(ExitButton exitButton) throws URISyntaxException, IOException;
    void visitLevelButton(LevelButton levelButton) throws IOException, URISyntaxException;
}

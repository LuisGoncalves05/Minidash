package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class LevelButton implements MenuOption {
    private final int levelNumber;

    public LevelButton(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    @Override
    public void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException {
        visitor.visitLevelButton(this);
    }
}

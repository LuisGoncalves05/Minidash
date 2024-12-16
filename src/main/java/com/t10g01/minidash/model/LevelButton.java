package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class LevelButton implements MenuOption {
    private final int level;

    public LevelButton(int level) {
        this.level = level;
    }

    @Override
    public void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException {
        visitor.visitLevelButton(this);
    }

    public int getLevel() {
        return level;
    }
}

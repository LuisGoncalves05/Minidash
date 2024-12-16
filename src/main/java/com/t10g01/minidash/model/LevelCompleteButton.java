package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class LevelCompleteButton implements MenuOption {
    @Override
    public void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException {
        visitor.acceptLevelComplete(this);
    }
}

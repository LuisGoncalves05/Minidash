package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.LevelButton;
import com.t10g01.minidash.model.MenuModel;

import java.io.IOException;
import java.util.Arrays;

public class LevelMenuState extends MenuState {
    public LevelMenuState(Game game) throws IOException {
        super(game);
    }

    @Override
    protected MenuModel createModel() {
        return new MenuModel(Arrays.asList(new LevelButton(1), new LevelButton(2), new LevelButton(3)));
    }
}

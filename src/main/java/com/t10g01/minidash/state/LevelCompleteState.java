package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.LevelCompleteButton;
import com.t10g01.minidash.model.MenuModel;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LevelCompleteState extends MenuState {
    public LevelCompleteState(Game game) throws IOException {
        super(game);
    }

    @Override
    protected MenuModel createModel() {
        return new MenuModel(List.of(new LevelCompleteButton()));
    }
}

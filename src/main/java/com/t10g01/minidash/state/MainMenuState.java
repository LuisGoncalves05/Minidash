package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.ExitButton;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.model.LevelsButton;

import java.io.IOException;
import java.util.Arrays;

public class MainMenuState extends MenuState {
    public MainMenuState(Game game) throws IOException {
        super(game);
    }

    @Override
    protected MenuModel createModel() throws IOException {
        return new MenuModel(Arrays.asList(new LevelsButton(), new ExitButton()));
    }
}

package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.utils.Action;
import com.t10g01.minidash.view.LevelView;

public class LevelController extends Controller<LevelModel> {
    public LevelController(LevelModel levelModel, Game game) {
       super(levelModel, game);
    }

    public boolean run() {
        return false;
    }

    @Override
    public void step(Action action, double deltaTime) {}
}

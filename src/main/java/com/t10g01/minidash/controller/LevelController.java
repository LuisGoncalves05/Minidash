package com.t10g01.minidash.controller;

import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.view.LevelView;

public class LevelController {
    private LevelModel levelModel;
    private LevelView levelView;

    public LevelController(LevelModel levelModel, LevelView levelView) {
        this.levelModel = levelModel;
        this.levelView = levelView;
    }

    public boolean run() {
        return false;
    }
}

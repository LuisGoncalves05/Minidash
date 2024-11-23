package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.utils.LevelAction;

public class LevelController extends Controller<LevelModel, LevelAction> {
    public LevelController(LevelModel levelModel, Game game) {
       super(levelModel, game);
    }


    @Override
    public void step(LevelAction levelAction, double deltaTime) {

    }
}

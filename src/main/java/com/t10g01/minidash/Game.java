package com.t10g01.minidash;

import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.view.LevelView;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.model.Player;

import java.util.ArrayList;

public class Game {
    public static void main(String[] args) {
        LevelView levelView = new LevelView();
        Player player = new Player(1.1, 1.2);
        LevelModel levelModel = new LevelModel(0, 0, player, new ArrayList<>());
        LevelController levelController = new LevelController(levelModel, levelView);
        levelController.run();
    }
}
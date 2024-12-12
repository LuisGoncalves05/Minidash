package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.utils.LevelAction;
import com.t10g01.minidash.view.View;
import com.t10g01.minidash.view.LevelView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelState extends State<LevelModel, LevelAction> {
    public LevelState(Game game, IOAdapter ioAdapter, GameSettings gameSettings) throws IOException {
        super(game, ioAdapter, gameSettings);
    }

    @Override
    protected LevelModel createModel() {
        // Temporary fix while a level loader is not implemented
        Player player = new Player(4, 1);
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < 100; i++) elements.add(new Block(i, 0));

        elements.add(new Spike(10, 1));

        elements.add(new Spike(20, 1));

        elements.add(new Spike(30, 1));
        elements.add(new Block(31, 1));
        elements.add(new Spike(32, 1));
        elements.add(new Spike(33, 1));
        elements.add(new Platform(34, 2));
        elements.add(new Spike(34, 1));
        elements.add(new Spike(35, 1));
        elements.add(new Spike(35, 1));
        elements.add(new Spike(36, 1));
        elements.add(new Spike(37, 1));
        elements.add(new Spike(38, 1));
        elements.add(new Spike(39, 1));
        elements.add(new Platform(37, 3));

        elements.add(new Spike(45, 1));
        elements.add(new Spike(46, 1));

        for (int i = 50; i < 55; i++) elements.add(new Block(i, 1));
        elements.add(new Spike(55, 1));
        elements.add(new Spike(56, 1));
        for (int i = 57; i < 65; i++) elements.add(new Block(i, 1));
        for (int i = 65; i < 85; i++) elements.add(new Spike(i, 1));
        elements.add(new Platform(68, 2));
        elements.add(new Platform(71, 3));
        elements.add(new Platform(74, 4));
        elements.add(new Platform(77, 5));
        elements.add(new Platform(80, 6));
        elements.add(new Platform(83, 7));
        for (int i = 83; i < 100; i++) elements.add(new Block(i, 1));

        return new LevelModel(10, 50, player, elements);
    }

    @Override
    protected Controller<LevelModel, LevelAction> createController() {
        return new LevelController(model, this.game);
    }

    @Override
    protected View<LevelModel> createView() {
        return new LevelView(this.model, this.ioAdapter, this.gameSettings);
    }

    @Override
    protected LevelAction getAction() {
        if (ioAdapter.isPressed(' ')) return LevelAction.JUMP;
        if (ioAdapter.isPressed('q')) return LevelAction.EXIT;
        return LevelAction.NULL;
    }
}
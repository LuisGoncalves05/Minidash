package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.Block;
import com.t10g01.minidash.model.Collidable;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.model.Player;
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
        Player player = new Player(10, 1);
        List<Collidable> collidables = new ArrayList<>();
        for (int i = 0; i < 50; i++) collidables.add(new Block(i, 0));
        collidables.add(new Block(20, 2));
        collidables.add(new Block(23, 3));
        for (int i = 26; i < 50; i++) collidables.add(new Block(i, 3));
        for (int i = 0; i < 6; i++) collidables.add(new Block(45, i));
        for (int i = 0; i < 8; i++) collidables.add(new Block(50, i));
        return new LevelModel(10, 50, player, collidables);
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
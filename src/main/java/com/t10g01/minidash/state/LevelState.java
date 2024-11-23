package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.Block;
import com.t10g01.minidash.model.Collidable;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.model.Player;
import com.t10g01.minidash.utils.LevelAction;
import com.t10g01.minidash.view.LevelView;
import com.t10g01.minidash.view.View;

import java.util.ArrayList;
import java.util.List;

public class LevelState extends State<LevelModel, LevelAction> {
    public LevelState(Game game, IOAdapter ioAdapter) {
        super(game, ioAdapter);
    }

    @Override
    protected LevelModel createModel() {
        // Temporary fix while a level loader is not implemented
        Player player = new Player(10, 1);
        List<Collidable> collidables = new ArrayList<>();
        for (int i = 0; i < 50; i++) collidables.add(new Block(i, 0));
        return new LevelModel(10, 50, player, collidables);
    }

    @Override
    protected Controller<LevelModel, LevelAction> createController() {
        return new LevelController(this.createModel(), this.game);
    }

    @Override
    protected View<LevelModel> createView() {
        return new LevelView(this.model, this.ioAdapter);
    }

    @Override
    protected LevelAction getAction() {
        if (ioAdapter.isPressed(' ')) return LevelAction.JUMP;
        if (ioAdapter.isPressed('q')) return LevelAction.EXIT;
        return LevelAction.NULL;
    }
}

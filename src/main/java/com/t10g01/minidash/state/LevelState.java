package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.view.LevelView;
import com.t10g01.minidash.view.View;

public class LevelState extends State<LevelModel> {
    public LevelState(LevelModel model, Game game, IOAdapter ioAdapter) {
        super(model, game, ioAdapter);
    }

    @Override
    protected Controller<LevelModel> getController() {
        return new LevelController(this.getModel(), this.getGame());
    }

    @Override
    protected View<LevelModel> getView() {
        return new LevelView(this.getModel(), this.getIOAdapter());
    }

    @Override
    public void step() {}
}

package com.t10g01.minidash.view;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.Position;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.Block;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

public class LevelView extends View<LevelModel> implements CollidableVisitor {

    public LevelView(LevelModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
    }

    public void draw() {

    }

    public void visitBlock(Block block) {
        //draw block
    }

}

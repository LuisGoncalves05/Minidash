package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.*;

public class MenuView extends View<MenuModel> implements MenuOptionVisitor {

    public MenuView(MenuModel model, IOAdapter ioAdapter) {
        super(model, ioAdapter);
    }

    @Override
    public void draw() {

    }

    @Override
    public void visitPlayButton(PlayButton playButton) {

    }

    @Override
    public void visitExitButton(ExitButton exitButton) {

    }
}

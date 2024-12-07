package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.ExitButton;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.model.MenuOptionVisitor;
import com.t10g01.minidash.model.PlayButton;
import com.t10g01.minidash.utils.MenuAction;

public class MenuController extends Controller<MenuModel, MenuAction> implements MenuOptionVisitor {

    public MenuController(MenuModel model, Game game) {
        super(model, game);
    }

    @Override
    public void step(MenuAction action, double deltaTime) {}

    @Override
    public void visitPlayButton(PlayButton playButton) {

    }

    @Override
    public void visitExitButton(ExitButton exitButton) {

    }
}

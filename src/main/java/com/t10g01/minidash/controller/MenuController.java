package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.utils.MenuAction;

public class MenuController extends Controller<MenuModel, MenuAction> {

    public MenuController(MenuModel model, Game game) {
        super(model, game);
    }

    @Override
    public void step(MenuAction action, double deltaTime) {

    }
}

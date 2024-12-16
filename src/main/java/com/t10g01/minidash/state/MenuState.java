package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.MenuController;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.utils.MenuAction;
import com.t10g01.minidash.view.MenuView;
import com.t10g01.minidash.view.View;

import java.io.IOException;

public abstract class MenuState extends State<MenuModel, MenuAction> {
    public MenuState(Game game) throws IOException {
        super(game);
    }

    @Override
    protected Controller<MenuModel, MenuAction> createController() {
        return new MenuController(model, this.game);
    }

    @Override
    protected View<MenuModel> createView() {
        return new MenuView(model, ioAdapter, gameSettings);
    }

    @Override
    protected MenuAction getAction() {
        if (ioAdapter.isPressed('w') || ioAdapter.isPressed('k'))
            return MenuAction.UP;
        if (ioAdapter.isPressed('s') || ioAdapter.isPressed('j'))
            return MenuAction.DOWN;
        if (ioAdapter.isPressed('q'))
            return MenuAction.EXIT;
        if (ioAdapter.isPressed(' '))
            return MenuAction.SELECT;
        return MenuAction.NULL;
    }

    @Override
    public MenuState reset() {
        this.model.setSelected(0);
        return this;
    }
}

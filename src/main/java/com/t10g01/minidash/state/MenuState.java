package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.MenuController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.utils.MenuAction;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.MenuView;
import com.t10g01.minidash.view.View;

import java.io.IOException;

public class MenuState extends State<MenuModel, MenuAction> {
    public MenuState(Game game, IOAdapter ioAdapter, GameSettings gameSettings) throws IOException {
        super(game, ioAdapter, gameSettings);
    }

    @Override
    protected MenuModel createModel() {
        return new MenuModel();
    }

    @Override
    protected Controller<MenuModel, MenuAction> createController() {
        return new MenuController(model, this.game);
    }

    @Override
    protected View<MenuModel> createView() {
        return new MenuView(model, this.ioAdapter);
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
    public void step(double deltaTime) throws IOException {
        this.createController().step(getAction(), deltaTime);
    }
}

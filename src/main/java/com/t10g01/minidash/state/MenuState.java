package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.MenuController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.view.MenuView;
import com.t10g01.minidash.view.View;

public class MenuState extends State<MenuModel> {
    public MenuState(MenuModel model, Game game, IOAdapter ioAdapter) {
        super(model, game, ioAdapter);
    }

    @Override
    protected Controller<MenuModel> getController() {
        return new MenuController(this.getModel(), this.getGame());
    }

    @Override
    protected View<MenuModel> getView() {
        return new MenuView(this.getModel(), this.getIOAdapter());
    }

    @Override
    public void step() {}
}

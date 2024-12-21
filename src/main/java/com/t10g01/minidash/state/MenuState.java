package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.MenuController;
import com.t10g01.minidash.model.MenuModel;
import com.t10g01.minidash.utils.MenuAction;
import com.t10g01.minidash.view.MenuView;

import java.io.IOException;

public abstract class MenuState extends State<MenuModel, MenuAction> {
    public MenuState(Game game) throws IOException {
        super(game);
        this.model = createModel();
        this.controller = new MenuController(this.game, model);
        this.view = new MenuView(model, output, gameSettings);
    }

    @Override
    public MenuState reset() {
        this.model.setSelected(0);
        return this;
    }

    @Override
    protected MenuAction getAction() {
        if (input.isPressed('w') || input.isPressed('k'))
            return MenuAction.UP;
        if (input.isPressed('s') || input.isPressed('j'))
            return MenuAction.DOWN;
        if (input.isPressed('q'))
            return MenuAction.EXIT;
        if (input.isPressed(' '))
            return MenuAction.SELECT;
        return MenuAction.NULL;
    }

    protected abstract MenuModel createModel() throws IOException;
}

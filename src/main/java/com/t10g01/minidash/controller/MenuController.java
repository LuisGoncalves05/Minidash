package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.state.LevelMenuState;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.utils.MenuAction;

import java.io.IOException;
import java.net.URISyntaxException;

public class MenuController extends Controller<MenuModel, MenuAction> implements MenuOptionVisitor {

    private double elapsedTime = 0;

    public MenuController(MenuModel model, Game game) {
        super(model, game);
    }

    @Override
    public void step(MenuAction action, double deltaTime) throws URISyntaxException, IOException {
        elapsedTime += deltaTime;

        if (action == MenuAction.NULL) return;
        double actionCoolDown = 0.25;
        if (elapsedTime < actionCoolDown) return;
        elapsedTime = 0;

        if (action == MenuAction.EXIT) game.setState(null);
        else if (action == MenuAction.SELECT) model.getOptions().get(model.getSelected()).accept(this);
        else {
            int optionCount = model.getOptions().size();
            // Java's % operator returns the remainder and not the modulo. Hence, (model.getSelected() - 1) % optionCount
            // is not feasible
            if (action == MenuAction.UP) model.setSelected((model.getSelected() + optionCount - 1) % optionCount);
            else model.setSelected((model.getSelected() + 1) % optionCount);
        }
    }

    @Override
    public void visitLevelsButton(LevelsButton levelsButton) throws IOException {
        game.setState(new LevelMenuState(game));
    }

    @Override
    public void visitExitButton(ExitButton exitButton) {
        game.setState(null);
    }

    @Override
    public void visitLevelButton(LevelButton levelButton) throws IOException {
        game.setState(new LevelState(game, game.getIoAdapter(), game.getGameSettings(), levelButton.getLevel()));
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

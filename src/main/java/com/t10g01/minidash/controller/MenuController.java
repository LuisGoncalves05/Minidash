package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.state.LevelMenuState;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.state.MainMenuState;
import com.t10g01.minidash.utils.MenuAction;

import java.io.IOException;
import java.net.URISyntaxException;

public class MenuController extends Controller<MenuModel, MenuAction> implements MenuOptionVisitor {
    private double elapsedTime = 0;

    public MenuController(Game game, MenuModel model) {
        super(model, game);
    }

    @Override
    public void step(MenuAction action, double deltaTime) throws URISyntaxException, IOException {
        elapsedTime += deltaTime;

        if (action == MenuAction.NULL) return;
        double actionCoolDown = 0.25;
        if (elapsedTime < actionCoolDown) return;
        elapsedTime = 0;

        int optionCount = model.getOptions().size();

        if (action == MenuAction.EXIT) game.setState(null);
        else if (action == MenuAction.SELECT) model.getOptions().get(model.getSelected()).accept(this);
        // Java's % operator returns the remainder and not the modulo. Hence, (model.getSelected() - 1) % optionCount
        // is not feasible
        else if (action == MenuAction.UP) model.setSelected((model.getSelected() + optionCount - 1) % optionCount);
        else model.setSelected((model.getSelected() + 1) % optionCount);
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
        game.setState(new LevelState(game, levelButton.level() - 1));
    }

    @Override
    public void acceptLevelComplete(LevelCompleteButton levelCompleteButton) throws IOException {
        game.setState(new MainMenuState(game));
    }

    // Setters used in tests
    public double getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

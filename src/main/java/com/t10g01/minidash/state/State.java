package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.View;

import java.io.IOException;

public abstract class State<T, U> {

    protected final T model;
    protected final Controller<T, U> controller;
    protected final View<T> view;
    protected final IOAdapter ioAdapter;
    protected final Game game;
    protected final GameSettings gameSettings;

    public State(Game game, IOAdapter ioAdapter, GameSettings gameSettings) throws IOException {
        this.game = game;
        this.gameSettings = gameSettings;
        this.ioAdapter = ioAdapter;
        this.model = createModel();
        this.controller = createController();
        this.view = createView();
    }

    public void step(double deltaTime) throws IOException {
        controller.step(getAction(), deltaTime);
        view.draw();
    }

    protected abstract T createModel();
    protected abstract Controller<T, U> createController();
    protected abstract View<T> createView();

    protected abstract U getAction();
}

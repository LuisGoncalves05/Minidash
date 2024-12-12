package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class State<T, U> {
    protected T model;
    protected Controller<T, U> controller;
    protected View<T> view;
    protected IOAdapter ioAdapter;
    protected Game game;
    protected GameSettings gameSettings;

    public State(Game game, IOAdapter ioAdapter, GameSettings gameSettings) throws IOException {
        this.game = game;
        this.gameSettings = gameSettings;
        this.ioAdapter = ioAdapter;
        this.model = createModel();
        this.controller = createController();
        this.view = createView();
    }

    public State(){}

    public void step(double deltaTime) throws IOException, URISyntaxException {
        controller.step(getAction(), deltaTime);
        view.draw();
    }

    protected abstract T createModel() throws IOException;
    protected abstract Controller<T, U> createController() throws IOException;
    protected abstract View<T> createView() throws IOException;

    protected abstract U getAction();
}

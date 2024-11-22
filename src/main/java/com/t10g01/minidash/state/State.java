package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.View;

public abstract class State<T> {

    private final T model;

    private final Controller<T> controller;

    private final View<T> view;

    private final IOAdapter ioAdapter;

    private final Game game;

    private final GameSettings gameSettings;

    public State(T model, Game game, IOAdapter ioAdapter, GameSettings gameSettings) {
        this.model = model;
        this.controller = getController();
        this.view = getView();
        this.ioAdapter = ioAdapter;
        this.game = game;
        this.gameSettings = gameSettings;
    }

    public T getModel() {
        return model;
    }

    protected abstract Controller<T> getController();

    protected abstract View<T> getView();

    protected IOAdapter getIOAdapter() {
        return ioAdapter;
    }

    protected Game getGame() {
        return game;
    }

    protected GameSettings getGameSettings() {}

    public abstract void step();

}

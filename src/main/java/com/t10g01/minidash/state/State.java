package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.View;

import java.io.IOException;

public abstract class State<T> {

    private final T model;

    private final Controller<T> controller;

    private final View<T> view;

    private final IOAdapter ioAdapter;

    private final Game game;

    private final GameSettings gameSettings;

    public State(T model, Game game, IOAdapter ioAdapter, GameSettings gameSettings) throws IOException {
        this.game = game;
        this.gameSettings = gameSettings;
        this.model = model;
        this.ioAdapter = ioAdapter;
        this.controller = getController();
        this.view = getView();
    }

    public T getModel() {
        return model;
    }

    protected abstract Controller<T> getController();

    protected abstract View<T> getView() throws IOException;

    protected IOAdapter getIOAdapter() {
        return ioAdapter;
    }

    protected Game getGame() {
        return game;
    }

    protected GameSettings getGameSettings() {
        return gameSettings;
    }

    public abstract void step();

}

package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.utils.Action;

public abstract class Controller<T> {

    private final T model;

    private final Game game;

    public Controller(T model, Game game) {
        this.model = model;
        this.game = game;
    }

    T getModel() {
        return model;
    }

    Game getGame() {
        return game;
    }

    public abstract void step(Action action, double deltaTime);

}

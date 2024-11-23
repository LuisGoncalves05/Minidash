package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;

public abstract class Controller<T, U> {

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

    public abstract void step(U action, double deltaTime);

}

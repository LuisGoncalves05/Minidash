package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.view.View;

public abstract class State<T, U> {

    protected final T model;
    protected final Controller<T, U> controller;
    protected final View<T> view;
    protected final IOAdapter ioAdapter;
    protected final Game game;

    public State(Game game, IOAdapter ioAdapter) {
        this.model = createModel();
        this.controller = createController();
        this.view = createView();
        this.ioAdapter = ioAdapter;
        this.game = game;
    }

    public void step(double deltaTime) {
        controller.step(getAction(), deltaTime);
    }

    protected abstract T createModel();
    protected abstract Controller<T, U> createController();
    protected abstract View<T> createView();

    protected abstract U getAction();
}

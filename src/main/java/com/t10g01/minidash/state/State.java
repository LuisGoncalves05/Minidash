package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.ioadapter.InputAdapter;
import com.t10g01.minidash.ioadapter.OutputAdapter;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.view.View;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class State<T, U> {
    protected T model;
    protected Controller<T, U> controller;
    protected View<T> view;

    protected final InputAdapter inputAdapter;
    protected final OutputAdapter outputAdapter;
    protected final Game game;
    protected final GameSettings gameSettings;

    public State(Game game) throws IOException {
        this.game = game;
        this.gameSettings = game.getGameSettings();
        this.inputAdapter = game.getiAdapter();
        this.outputAdapter = game.getoAdapter();
    }

    public void step(double deltaTime) throws IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException {
        controller.step(getAction(), deltaTime);
        view.draw();
    }

    public abstract State<T, U> reset() throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    protected abstract U getAction();
}

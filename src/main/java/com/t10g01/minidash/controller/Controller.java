package com.t10g01.minidash.controller;

import com.t10g01.minidash.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Controller<T, U> {
    protected final T model;
    protected final Game game;

    public Controller(T model, Game game) {
        this.model = model;
        this.game = game;
    }

    public abstract void step(U action, double deltaTime) throws IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException;
}

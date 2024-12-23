package com.t10g01.minidash.view;

import com.t10g01.minidash.io.Output;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class View<T> {
    protected final T model;
    protected final Output output;

    public View(T model, Output output) {
        this.model = model;
        this.output = output;
    }

    public abstract void draw() throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException;

}

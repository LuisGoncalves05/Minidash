package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.OutputAdapter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class View<T> {
    protected final T model;
    protected final OutputAdapter outputAdapter;

    public View(T model, OutputAdapter outputAdapter) {
        this.model = model;
        this.outputAdapter = outputAdapter;
    }

    public abstract void draw() throws IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException;

}

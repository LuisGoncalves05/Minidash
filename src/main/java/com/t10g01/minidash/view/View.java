package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.IOAdapter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class View<T> {
    protected final T model;
    protected final IOAdapter ioAdapter;

    public View(T model, IOAdapter ioAdapter) {
        this.model = model;
        this.ioAdapter = ioAdapter;
    }

    public abstract void draw() throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException;

}

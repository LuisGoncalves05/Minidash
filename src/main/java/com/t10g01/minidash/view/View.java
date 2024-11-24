package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.IOAdapter;

import java.io.IOException;

public abstract class View<T> {

    protected final T model;

    protected final IOAdapter ioAdapter;

    public View(T model, IOAdapter ioAdapter) {
        this.model = model;
        this.ioAdapter = ioAdapter;
    }

    public abstract void draw() throws IOException;
}

package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.IOAdapter;

public abstract class View<T> {

    private final T model;

    private final IOAdapter ioAdapter;

    public View(T model, IOAdapter ioAdapter) {
        this.model = model;
        this.ioAdapter = ioAdapter;
    }

    public T getModel() {
        return model;
    }

    public IOAdapter getIoAdapter() {
        return ioAdapter;
    }

    public abstract void draw();
}

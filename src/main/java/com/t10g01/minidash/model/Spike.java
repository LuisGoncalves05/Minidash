package com.t10g01.minidash.model;

public class Spike extends Element {
    public Spike(int x, int y) {
        super(x, y);
    }

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visitSpike(this);
    }
}

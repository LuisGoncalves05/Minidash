package com.t10g01.minidash.model;

import java.io.IOException;

public interface ElementVisitor {
    void visitBlock(Block block) throws IOException;
    void visitSpike(Spike spike) throws IOException;
    void visitPlatform(Platform platform) throws IOException;
    void visitBoost(Boost boost);
}
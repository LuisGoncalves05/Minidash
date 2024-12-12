package com.t10g01.minidash.model;

import java.io.IOException;

public interface ElementVisitor {
    void visitBlock(Block block);
    void visitSpike(Spike spike);
    void visitPlatform(Platform platform);
    void visitLevelEnd(LevelEnd levelEnd) throws IOException;
}
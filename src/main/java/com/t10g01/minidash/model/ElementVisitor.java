package com.t10g01.minidash.model;

public interface ElementVisitor {
    void visitBlock(Block block);
    void visitSpike(Spike spike);
    void visitPlatform(Platform platform);
    void visitLevelEnd();
}
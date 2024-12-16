package com.t10g01.minidash.model;

import java.io.IOException;

public interface ElementVisitor {
    void visitBlock(Block block) throws IOException;
    void visitSpike(Spike spike) throws IOException;
    void visitReversedSpike(ReversedSpike reversedSpike) throws IOException;
    void visitPlatform(Platform platform) throws IOException;
    void visitBoost(Boost boost);
    void visitLevelEnd(LevelEnd levelEnd) throws IOException;
    void visitDoubleJump(DoubleJump doubleJump);
}
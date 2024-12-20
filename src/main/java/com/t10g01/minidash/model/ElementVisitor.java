package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface ElementVisitor {
    void visitBlock(Block block) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    void visitSpike(Spike spike) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    void visitReversedSpike(ReversedSpike reversedSpike) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    void visitPlatform(Platform platform) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    void visitBoost(Boost boost);
    void visitLevelEnd(LevelEnd levelEnd) throws IOException;
    void visitDoubleJump(DoubleJump doubleJump);
}
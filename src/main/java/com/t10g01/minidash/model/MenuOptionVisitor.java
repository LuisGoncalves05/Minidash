package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface MenuOptionVisitor {
    void visitLevelsButton(LevelsButton levelsButton) throws URISyntaxException, IOException;
    void visitExitButton(ExitButton exitButton) throws URISyntaxException, IOException;
    void visitLevelButton(LevelButton levelButton) throws IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException;
    void acceptLevelComplete(LevelCompleteButton levelCompleteButton) throws IOException, URISyntaxException;
}

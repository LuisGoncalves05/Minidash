package com.t10g01.minidash.model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public record LevelButton(int level) implements MenuOption {
    @Override
    public void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        visitor.visitLevelButton(this);
    }
}

package com.t10g01.minidash.sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface SoundPlayer {
    void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    void stopSound();
}

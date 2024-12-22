package com.t10g01.minidash.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WAVPlayer implements SoundPlayer {
    private static Clip clip;
    private final int levelNumber;

    public WAVPlayer(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    @Override
    public void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (clip != null) return;

        URL resource = getClass().getClassLoader().getResource("lvl" + levelNumber + ".wav");
        assert resource != null;

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource)) {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
    }

    @Override
    public void stopSound() {
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.flush();
        clip.close();
        clip = null;
    }
}

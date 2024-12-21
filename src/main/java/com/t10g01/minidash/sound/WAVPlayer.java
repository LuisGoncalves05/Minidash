package com.t10g01.minidash.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WAVPlayer implements SoundPlayer {
    private Clip clip;

    @Override
    public void playSound(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (clip != null) return;
        URL resource = getClass().getClassLoader().getResource(path);
        assert resource != null;

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource)) {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
    }

    @Override
    public void stopSound() {
        if (clip == null) return;
        clip.stop();
        clip.close();
        clip = null;
    }
}

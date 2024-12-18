package com.t10g01.minidash.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WAVPlayer implements SoundPlayer {
    Clip clip;
    int levelNumber;
    private boolean playing = false;


    public WAVPlayer(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    @Override
    public void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (playing) return;
        URL resource = getClass().getClassLoader().getResource("lvl" + levelNumber + ".wav");
        assert resource != null;
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        playing = true;
    }

    @Override
    public void stopSound() {
        if (playing) clip.stop();
    }
}

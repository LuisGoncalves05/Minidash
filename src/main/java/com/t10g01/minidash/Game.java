package com.t10g01.minidash;

import com.t10g01.minidash.io.Input;
import com.t10g01.minidash.io.LanternaIO;
import com.t10g01.minidash.io.Output;
import com.t10g01.minidash.state.MainMenuState;
import com.t10g01.minidash.state.State;
import com.t10g01.minidash.utils.GameSettings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        new Game().start();
    }

    private final GameSettings gameSettings;
    private final Input input;
    private final Output output;
    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        this.gameSettings = new GameSettings();
        LanternaIO lanternaIO = new LanternaIO(
                gameSettings.getCameraHeight() * gameSettings.getResolution(),
                gameSettings.getCameraWidth() * gameSettings.getResolution(),
                gameSettings.getBackgroundColor()
        );
        this.input = lanternaIO;
        this.output = lanternaIO;
        this.state = new MainMenuState(this);
    }

    public void start() throws InterruptedException, IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException {
        int maxFPS = 60;
        double minFrameTime = 1000.0 / maxFPS; // milliseconds per frame

        double lastFrame = System.currentTimeMillis();
        while (state != null) {
            double currentFrame = System.currentTimeMillis();
            double frameTime = (currentFrame - lastFrame) / 1000.0;
            state.step(frameTime);
            double elapsedTime = System.currentTimeMillis() - currentFrame;
            double sleepTime = Math.max(minFrameTime - elapsedTime, 0);
            lastFrame = currentFrame;
            Thread.sleep((int)sleepTime);
        }

        output.close();
    }

    public void resetState() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        state = state.reset();
    }

    public void setState(State state) {
        this.state = state;
    }

    public Input getInput() {
        return input;
    }

    public Output getOutput() {
        return output;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    // Constructor used for testing
    public Game(GameSettings gameSettings, Input input, Output output, State state) {
        this.gameSettings = gameSettings;
        this.input = input;
        this.output = output;
        this.state = state;
    }
}
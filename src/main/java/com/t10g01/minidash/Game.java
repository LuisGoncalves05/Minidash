package com.t10g01.minidash;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.ioadapter.LanternaIOAdapter;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.state.MainMenuState;
import com.t10g01.minidash.state.MenuState;
import com.t10g01.minidash.state.State;
import com.t10g01.minidash.utils.GameSettings;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        new Game().start();
    }

    private final GameSettings gameSettings;
    private final IOAdapter ioAdapter;
    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        this.gameSettings = new GameSettings(10, 20, 10);
        this.ioAdapter = new LanternaIOAdapter(
                gameSettings.getCameraHeight() * gameSettings.getResolution(),
                gameSettings.getCameraWidth() * gameSettings.getResolution(),
                gameSettings.getBackgroundColor()
        );
        this.state = new MainMenuState(this);
    }

    public void start() throws InterruptedException, IOException, URISyntaxException {
        int maxFPS = 30;
        double minFrameTime = 1000.0 / maxFPS; // milliseconds per frame

        double lastFrame = System.currentTimeMillis();
        while (state != null) {
            double currentFrame = System.currentTimeMillis();
            state.step((currentFrame - lastFrame) / 1000.0);
            lastFrame = currentFrame;

            double elapsedTime = System.currentTimeMillis() - currentFrame;
            double sleepTime = Math.max(elapsedTime - minFrameTime, 0);
            Thread.sleep((int)sleepTime);
        }

        ioAdapter.close();
    }

    public void resetState() throws IOException {
        state = state.reset();
    }

    public void setState(State state) {
        this.state = state;
    }

    public IOAdapter getIoAdapter() {
        return ioAdapter;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    // Constructor used for testing
    public Game(GameSettings gameSettings, IOAdapter ioAdapter, State state) {
        this.gameSettings = gameSettings;
        this.ioAdapter = ioAdapter;
        this.state = state;
    }
}
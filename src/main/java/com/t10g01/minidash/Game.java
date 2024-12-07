package com.t10g01.minidash;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.ioadapter.LanternaIOAdapter;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.state.MenuState;
import com.t10g01.minidash.state.State;
import com.t10g01.minidash.utils.GameSettings;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {

    private final GameSettings gameSettings;
    private final IOAdapter ioAdapter;
    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        this.gameSettings = new GameSettings(20, 15, 7);
        this.ioAdapter = new LanternaIOAdapter(
                gameSettings.getCameraHeight() * gameSettings.getResolution(),
                gameSettings.getCameraWidth() * gameSettings.getResolution(),
                gameSettings.getBackgroundColor()
        );
        this.state = new MenuState(this, ioAdapter, gameSettings);
    }

    public Game(GameSettings gameSettings, IOAdapter ioAdapter, State state) {
        this.gameSettings = gameSettings;
        this.ioAdapter = ioAdapter;
        this.state = state;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        new Game().start();
    }

    public void setState(State state) {
        this.state = state;
    }

    public IOAdapter getIoAdapter() {
        return ioAdapter;
    }

    public void start() throws InterruptedException, IOException, URISyntaxException {
        int FPS = 30;
        int frameTime = 1000 / FPS; // milliseconds per frame

        while (state != null) {
            long startTime = System.currentTimeMillis();

            state.step(frameTime / 1000.0);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        ioAdapter.close();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}
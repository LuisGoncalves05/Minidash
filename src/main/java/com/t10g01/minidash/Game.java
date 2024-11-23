package com.t10g01.minidash;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.ioadapter.LanternaIOAdapter;
import com.t10g01.minidash.model.Collidable;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.model.Player;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.state.State;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Game {

    private final IOAdapter ioAdapter;
    private final GameSettings gameSettings;

    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        this.gameSettings = new GameSettings(10, 15, 7);
        this.ioAdapter = new LanternaIOAdapter(
            gameSettings.getCameraHeight() * gameSettings.getResolution(),
            gameSettings.getCameraWidth() * gameSettings.getResolution(),
            gameSettings.getBackgroundColor()
        );
        this.state = new LevelState(
             new LevelModel(10, 60, new Player(10, 10), new ArrayList<Collidable>()),
            this,
            ioAdapter,
            gameSettings
        );
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game().start();
    }

    private void start() {

    }

    public void setState(State state) {
        this.state = state;
    }
}
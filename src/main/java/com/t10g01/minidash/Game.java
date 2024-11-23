package com.t10g01.minidash;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.ioadapter.LanternaIOAdapter;
import com.t10g01.minidash.model.Collidable;
import com.t10g01.minidash.model.LevelModel;
import com.t10g01.minidash.model.Player;
import com.t10g01.minidash.state.LevelState;
import com.t10g01.minidash.state.State;
import com.t10g01.minidash.utils.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Game {

    private final IOAdapter ioAdapter;

    private State state;

    public Game(IOAdapter ioAdapter) {
        this.ioAdapter = ioAdapter;
        this.state = new LevelState(
             this,
             ioAdapter
        );
    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        new Game(new LanternaIOAdapter(10, 10, new Color("#ffffff"))).start();
    }

    public void setState(State state) {
        this.state = state;
    }

    private void start() throws InterruptedException, IOException {
        int FPS = 100;
        int frameTime = 1000 / FPS; // milliseconds per frame

        while (state != null) {
            long startTime = System.currentTimeMillis();

            state.step(startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        ioAdapter.close();
    }
}
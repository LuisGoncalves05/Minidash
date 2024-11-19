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
                     new LevelModel(50, 50, new Player(10, 10), new ArrayList<Collidable>()),
                     this,
                     ioAdapter);
    }

    public static void main(String[] args) throws IOException, FontFormatException, URISyntaxException {
        new Game(new LanternaIOAdapter(10, 10, new Color("#ffffff"))).start();
    }

    private void start() {

    }

    public void setState(State state) {
        this.state = state;
    }
}
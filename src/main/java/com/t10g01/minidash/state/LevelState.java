package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.Controller;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.GameSettings;
import com.t10g01.minidash.utils.LevelAction;
import com.t10g01.minidash.view.View;
import com.t10g01.minidash.view.LevelView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LevelState extends State<LevelModel, LevelAction> {
    private final int levelNumber;
    public LevelState(Game game, IOAdapter ioAdapter, GameSettings gameSettings, int levelNumber) throws IOException {
        super();
        this.game = game;
        this.gameSettings = gameSettings;
        this.ioAdapter = ioAdapter;
        this.levelNumber = levelNumber;
        this.model = createModel();
        this.controller = createController();
        this.view = createView();
    }

    @Override
    protected LevelModel createModel() throws IOException {
        Scanner myReader = createScanner();

        int lines = myReader.nextInt();
        int columns = myReader.nextInt();
        myReader.nextLine();

        List<Element> elements = new ArrayList<>();
        for (int x = 0; x < lines; x++) {
            String data = myReader.nextLine();
            for (int y = 0; y < columns; y++) {
                Element element = getElement(data, x, y);
                if (element != null) elements.add(element);
            }
        }

        int posX = myReader.nextInt();
        int posY = myReader.nextInt();
        myReader.close();
        return new LevelModel(new Player(posX, posY), elements);
    }

    private Element getElement(String data, int x, int y) {
        return switch (data.charAt(y)) {
            case '#' -> new Block(x, y);
            case '|' -> new Platform(x, y);
            case '>' -> new Spike(x, y);
            //case ')' -> new Boost(x, y);
            default -> null;
        };
    }

    private Scanner createScanner() throws IOException {
        URL level = getClass().getClassLoader().getResource("lvl" + this.levelNumber + ".txt");
        assert level != null;
        File levelFile = new File(level.getFile());
        return new Scanner(levelFile, UTF_8);
    }

    @Override
    protected Controller<LevelModel, LevelAction> createController() {
        return new LevelController(model, this.game);
    }

    @Override
    protected View<LevelModel> createView() {
        return new LevelView(model, this.ioAdapter, this.gameSettings);
    }

    @Override
    protected LevelAction getAction() {
        if (ioAdapter.isPressed(' ')) return LevelAction.JUMP;
        if (ioAdapter.isPressed('q')) return LevelAction.EXIT;
        return LevelAction.NULL;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
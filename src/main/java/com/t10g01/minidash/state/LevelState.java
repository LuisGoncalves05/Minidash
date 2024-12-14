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
        /*
        (Level represented rotated 90 degrees in the txt file because then elements are read sorted by their respective x coordinates)
        Reads level in the format:
            l c
            vertical line of elements
            vertical line of elements
            (...)
            x y
        Where l is the number of lines that represent the level and c the number of elements in each line.
        x and y are the initial coordinates of the player in this level
        */

        Scanner LevelScanner = createLevelScanner();

        int lines = LevelScanner.nextInt();
        int columns = LevelScanner.nextInt();
        LevelScanner.nextLine();

        List<Element> elements = new ArrayList<>();

        for (int x = 0; x < lines; x++) {
            String data = LevelScanner.nextLine();
            for (int y = 0; y < columns; y++) {
                Element element = getElement(data, x, y);
                if (element != null) elements.add(element);
            }
        }

        int posX = LevelScanner.nextInt();
        int posY = LevelScanner.nextInt();
        LevelScanner.close();
        return new LevelModel(new Player(posX, posY), elements);
    }

    public static Element getElement(String data, int x, int y) throws IndexOutOfBoundsException {
        return switch (data.charAt(y)) {
            case '#' -> new Block(x, y);
            case '|' -> new Platform(x, y);
            case '>' -> new Spike(x, y);
            case ')' -> new Boost(x, y);
            default -> null;
        };
    }

    private Scanner createLevelScanner() throws IOException {
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
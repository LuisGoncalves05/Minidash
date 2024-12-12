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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LevelState extends State<LevelModel, LevelAction> {
    private final int levelNumber;
    public LevelState(Game game, IOAdapter ioAdapter, GameSettings gameSettings, int levelNumber) throws IOException {
        super(game, ioAdapter, gameSettings);
        this.levelNumber = levelNumber;
    }

    @Override
    protected LevelModel createModel() throws IOException {
        int lines, columns, posX, posY;
        List<Element> elements = new ArrayList<>();
        URL level = getClass().getClassLoader().getResource("Lvl" + levelNumber + ".txt");
        assert level != null;
        File levelFile = new File(level.getFile());
        System.out.println("Loading level : Lvl" + levelNumber + ".txt");
        Scanner myReader = new Scanner(levelFile, UTF_8);
        lines = myReader.nextInt();
        columns = myReader.nextInt();
        myReader.nextLine();

        for (int x = 0; x < lines; x++) {
            String data = myReader.nextLine();
            for (int y = 0; y < columns; y++) {
                 Element element = switch (data.charAt(y)) {
                    case '#' -> new Block(x, y);
                    case '|' -> new Platform(x, y);
                    case '>' -> new Spike(x, y);
                    default -> null;
                };
                if (element != null) elements.add(element);
            }
        }
        posX = myReader.nextInt();
        posY = myReader.nextInt();
        myReader.close();
        Player player = new Player(posX, posY);
        return new LevelModel(10, 50, player, elements);
    }

    @Override
    protected Controller<LevelModel, LevelAction> createController() {
        return new LevelController(model, this.game);
    }

    @Override
    protected View<LevelModel> createView() {
        return new LevelView(this.model, this.ioAdapter, this.gameSettings);
    }

    @Override
    protected LevelAction getAction() {
        if (ioAdapter.isPressed(' ')) return LevelAction.JUMP;
        if (ioAdapter.isPressed('q')) return LevelAction.EXIT;
        return LevelAction.NULL;
    }
}
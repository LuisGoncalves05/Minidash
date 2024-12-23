package com.t10g01.minidash.state;

import com.t10g01.minidash.Game;
import com.t10g01.minidash.controller.LevelController;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.LevelAction;
import com.t10g01.minidash.view.LevelView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LevelState extends State<LevelModel, LevelAction> {
    private final int levelNumber;

    public LevelState(Game game, int levelNumber) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super(game);
        this.levelNumber = levelNumber;
        this.model = createModel();
        this.controller = new LevelController(model, game);
        this.view = new LevelView(model, game.getOutput(), game.getGameSettings());
    }

    @Override
    protected LevelAction getAction() {
        if (input.isPressed(' ')) return LevelAction.JUMP;
        if (input.isPressed('q')) return LevelAction.EXIT;
        return LevelAction.NULL;
    }

    @Override
    public LevelState reset() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        return new LevelState(game, levelNumber);
    }

    /*
     *  Reads level in the format:
     *      l c
     *      vertical line of elements
     *      vertical line of elements
     *      [...]
     *      x y
     *
     *  Where l is the number of lines that represent the level and c the number of elements in each line.
     *  x and y are the initial coordinates of the player in this level
     *
     *  Levels are rotated by 90 degrees for ease of reading - elements are read sorted by their x coordinates
     */
    protected LevelModel createModel() throws IOException {
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

        int playerX = LevelScanner.nextInt();
        int playerY = LevelScanner.nextInt();

        LevelScanner.close();
        return new LevelModel(elements, playerX, playerY, levelNumber);
    }

    public static Element getElement(String data, int x, int y) throws IndexOutOfBoundsException {
        return switch (data.charAt(y)) {
            case '#' -> new Block(x, y);
            case '|' -> new Platform(x, y);
            case '>' -> new Spike(x, y);
            case '<' -> new ReversedSpike(x, y);
            case ')' -> new Boost(x, y);
            case '_' -> new LevelEnd(x, y);
            case '*' -> new DoubleJump(x, y);
            case ' ' -> null;
            default -> throw new IllegalArgumentException(y + " unknown character " + data.charAt(y));
        };
    }

    private Scanner createLevelScanner() throws IOException {
        URL level = getClass().getClassLoader().getResource("lvl" + this.levelNumber + ".txt");
        assert level != null;
        File levelFile = new File(level.getFile());
        return new Scanner(levelFile, UTF_8);
    }

    // Constructor used for testing
    public LevelState(Game game) throws IOException {
        super(game);
        this.levelNumber = -1;
        this.model = createModel();
    }
}
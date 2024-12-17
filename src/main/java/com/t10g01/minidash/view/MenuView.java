package com.t10g01.minidash.view;

import com.t10g01.minidash.ioadapter.IOAdapter;
import com.t10g01.minidash.model.*;
import com.t10g01.minidash.utils.Color;
import com.t10g01.minidash.utils.GameSettings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MenuView extends View<MenuModel> implements MenuOptionVisitor {
    private final GameSettings gameSettings;
    private int renderedOptions;

    public MenuView(MenuModel model, IOAdapter ioAdapter, GameSettings gameSettings) {
        super(model, ioAdapter);
        this.gameSettings = gameSettings;
    }

    @Override
    public void draw() throws URISyntaxException, IOException {
        renderedOptions = 0;

        ioAdapter.clear();
        for (MenuOption option : model.getOptions()) option.accept(this);
        ioAdapter.refresh();
    }

    @Override
    public void visitLevelsButton(LevelsButton levelsButton) throws URISyntaxException, IOException {
        drawOption("levels.png");
    }

    @Override
    public void visitExitButton(ExitButton exitButton) throws URISyntaxException, IOException {
        drawOption("exit.png");
    }

    @Override
    public void visitLevelButton(LevelButton levelButton) throws IOException, URISyntaxException {
        drawOption("level" + levelButton.getLevelNumber() + ".png");
    }

    @Override
    public void acceptLevelComplete(LevelCompleteButton levelCompleteButton) throws IOException, URISyntaxException {
        drawOption("complete.png");
    }

    private void drawOption(String spritePath) throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource(spritePath);
        assert resource != null;
        BufferedImage sprite = ImageIO.read(new File(resource.toURI()));

        int numberOptions = model.getOptions().size();
        // Option sprites are assumed to be 30px tall and there is a 20px margin to the top of the screen
        int offsetY = (renderedOptions + 1) * (ioAdapter.getScreenHeight() - numberOptions * 30) / (numberOptions + 1) + renderedOptions * 30;
        int offsetX = (ioAdapter.getScreenWidth() - sprite.getWidth()) / 2;

        Color color = model.getSelected() == renderedOptions? gameSettings.getSelectedOptionColor() : gameSettings.getMenuOptionColor();

        for (int i = 0; i < sprite.getHeight(); i++) {
            for (int j = 0; j < sprite.getWidth(); j++) {
                if (sprite.getRGB(j, i) != 0) {
                    ioAdapter.drawPixel(offsetX + j, ioAdapter.getScreenHeight() - i - offsetY, color);
                }
            }
        }

        renderedOptions++;
    }
}

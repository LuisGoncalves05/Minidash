package com.t10g01.minidash.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.t10g01.minidash.utils.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaOutput implements Output {

    private int height;
    private Screen screen;
    private Color backgroundColor;
    private TextGraphics graphics;

    public LanternaOutput(int width, int height, Color backgroundColor) throws IOException, FontFormatException, URISyntaxException {
        this.height = height;

        URL resource = getClass().getClassLoader().getResource("square.ttf");
        assert resource != null;
        File fontFile = new File(resource.toURI());

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font newfont = font.deriveFont(Font.PLAIN, 4);

        AWTTerminalFontConfiguration cfg = AWTTerminalFontConfiguration.newInstance(newfont);


        Terminal terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(width, height))
                .setTerminalEmulatorFontConfiguration(cfg)
                .setForceAWTOverSwing(true)
                .createTerminal();

        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        screen.clear();
        graphics = screen.newTextGraphics();

        TextColor color = new TextColor.RGB(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
        graphics.setBackgroundColor(color);
        graphics.fillRectangle(new TerminalPosition(0,0), new TerminalSize(width, height), ' ');

        refresh();
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        TextColor pixelColor = new TextColor.RGB(color.getRed(), color.getGreen(), color.getBlue());

        graphics.setBackgroundColor(pixelColor);
        graphics.fillRectangle(new TerminalPosition(x, height - y), new TerminalSize(1, 1), ' ');
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }
}

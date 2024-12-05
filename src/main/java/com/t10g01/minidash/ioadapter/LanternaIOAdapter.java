package com.t10g01.minidash.ioadapter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import com.t10g01.minidash.utils.Color;

public class LanternaIOAdapter implements IOAdapter {

    private final int screenHeight;
    private final int screenWidth;
    private final Color backgroundColor;

    private final Terminal terminal;
    private final Screen screen;
    private final TextGraphics graphics;
    private final Set<Character> pressedKeys = new HashSet<>();

    static final String FONT_PATH = "square.ttf";

    public LanternaIOAdapter(int screenHeight, int screenWidth, Color backgroundColor) throws IOException, FontFormatException, URISyntaxException {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.backgroundColor = backgroundColor;

        this.terminal = createTerminal();
        this.screen = createScreen();
        this.graphics = screen.newTextGraphics();

        clear();
        refresh();
        ((AWTTerminalFrame) terminal).getComponent(0).addKeyListener(createKeyAdapter());
    }

    public boolean isPressed(char c) {
        return pressedKeys.contains(c);
    }

    public void clear() {
        screen.clear();
        TextColor color = new TextColor.RGB(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
        graphics.setBackgroundColor(color);
        graphics.fill(' ');
    }

    public void drawPixel(int x, int y, Color color) {
        TextColor pixelColor = new TextColor.RGB(color.getRed(), color.getGreen(), color.getBlue());
        graphics.setBackgroundColor(pixelColor);
        graphics.fillRectangle(new TerminalPosition(x, screenHeight - y - 1), new TerminalSize(1, 1), ' ');
    }

    public void drawRectangle(int x, int y, int width, int height, Color color) {
        TextColor pixelColor = new TextColor.RGB(color.getRed(), color.getGreen(), color.getBlue());
        graphics.setBackgroundColor(pixelColor);
        graphics.fillRectangle(new TerminalPosition(x, screenHeight - y - height), new TerminalSize(width, height), ' ');
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public void close() throws IOException {
        screen.close();
    }

    Terminal createTerminal() throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(FONT_PATH);
        assert resource != null;
        File fontFile = new File(resource.toURI());

        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font newfont = font.deriveFont(Font.PLAIN, 2);

        AWTTerminalFontConfiguration cfg = AWTTerminalFontConfiguration.newInstance(newfont);

        return new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(screenWidth, screenHeight))
                .setTerminalEmulatorFontConfiguration(cfg)
                .setForceAWTOverSwing(true)
                .createTerminal();
    }

    Screen createScreen() throws IOException {
        Screen screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        return screen;
    }

    private KeyAdapter createKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                LanternaIOAdapter.this.keyPressed(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                LanternaIOAdapter.this.keyReleased(e.getKeyChar());
            }
        };
    }

    public void keyPressed(char c) {
        pressedKeys.add(c);
    }

    public void keyReleased(char c) {
        pressedKeys.remove(c);
    }

    // Constructor used for testing
    public LanternaIOAdapter(Terminal terminal, Screen screen, TextGraphics graphics, int screenHeight, int screenWidth, Color backgroundColor) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.backgroundColor = backgroundColor;
        this.terminal = terminal;
        this.screen = screen;
        this.graphics = graphics;
    }
}

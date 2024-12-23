package com.t10g01.minidash.io

import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.t10g01.minidash.utils.Color
import spock.lang.Shared
import spock.lang.Specification
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.graphics.TextGraphics

class LanternaIOSpec extends Specification {
    @Shared
    Screen screen

    @Shared
    TextGraphics graphics

    @Shared
    Terminal terminal

    @Shared
    LanternaIO lanternaIO

    def setup() {
        screen = Mock(Screen)
        graphics = Mock(TextGraphics)
        terminal = Mock(Terminal)
        lanternaIO = Spy(LanternaIO, constructorArgs: [terminal, screen, graphics, 10, 10, Mock(Color)])
    }

    def "isPressed test"() {
        given:
        lanternaIO = new LanternaIO(terminal, screen, graphics, 10, 10, Mock(Color))

        expect:
        !lanternaIO.isPressed(' ' as Character)
        !lanternaIO.isPressed('e' as Character)

        when:
        lanternaIO.keyPressed(' ' as Character)

        then:
        lanternaIO.isPressed(' ' as Character)
        !lanternaIO.isPressed('e' as Character)

        when:
        lanternaIO.keyReleased(' ' as Character)

        then:
        !lanternaIO.isPressed(' ' as Character)
        !lanternaIO.isPressed('e' as Character)
    }

    def "clear test"() {
        when:
        lanternaIO.clear()

        then:
        1 * screen.clear()
        1 * graphics.setBackgroundColor(_)
        1 * graphics.fill(Character.valueOf(' ' as Character))
    }

    def "close test"() {
        when:
        lanternaIO.close()

        then:
        1 * screen.close()
    }

    def "refresh test"() {
        when:
        lanternaIO.refresh()

        then:
        1 * screen.refresh()
    }


    def "drawPixel test"(int x, int y, int xf, int yf, Color color, TextColor colorf) {
        when:
        lanternaIO.drawPixel(x, y, color)

        then:
        1 * graphics.setBackgroundColor(colorf)
        1 * graphics.fillRectangle(new TerminalPosition(xf, yf), new TerminalSize(1, 1), Character.valueOf(' ' as Character))

        where:
        x | y | xf | yf | color                | colorf
        1 | 1 | 1  | 8  | new Color("#000000") | new TextColor.RGB(0, 0, 0)
        5 | 6 | 5  | 3  | new Color("#FFFFFF") | new TextColor.RGB(255, 255, 255)
        3 | 9 | 3  | 0  | new Color("#FF0000") | new TextColor.RGB(255, 0, 0)
    }


    def "drawRectangle test"(int height, int width, int x, int y, int xf, int yf, Color color, TextColor colorf) {
        when:
        lanternaIO.drawRectangle(x, y, width, height, color)

        then:
        1 * graphics.setBackgroundColor(colorf)
        1 * graphics.fillRectangle(new TerminalPosition(xf, yf), new TerminalSize(width, height), Character.valueOf(' ' as Character))

        where:
        height | width | x | y | xf | yf | color                | colorf
        2      | 3     | 1 | 1 | 1  | 7  | new Color("#000000") | new TextColor.RGB(0, 0, 0)
        4      | 4     | 5 | 5 | 5  | 1  | new Color("#FFFFFF") | new TextColor.RGB(255, 255, 255)
        6      | 2     | 3 | 2 | 3  | 2  | new Color("#FF0000") | new TextColor.RGB(255, 0, 0)
    }

    def "drawCircle test with radius 2"() {
        given:
        def color = new Color("#000000")

        when:
        lanternaIO.drawCircle(2, 2, 2, color)

        then:

        1 * lanternaIO.drawPixel(0, 2, color)

        1 * lanternaIO.drawPixel(1, 1, color)
        1 * lanternaIO.drawPixel(1, 2, color)
        1 * lanternaIO.drawPixel(1, 3, color)

        1 * lanternaIO.drawPixel(2, 0, color)
        1 * lanternaIO.drawPixel(2, 1, color)
        1 * lanternaIO.drawPixel(2, 2, color)
        1 * lanternaIO.drawPixel(2, 3, color)
        1 * lanternaIO.drawPixel(2, 4, color)

        1 * lanternaIO.drawPixel(3, 1, color)
        1 * lanternaIO.drawPixel(3, 2, color)
        1 * lanternaIO.drawPixel(3, 3, color)

        1 * lanternaIO.drawPixel(4, 2, color)

        0 * lanternaIO.drawPixel(_, _, _)
    }

    def "drawCircle test with radius 3"() {
        given:
        def color = new Color("#FF0000")

        when:
        lanternaIO.drawCircle(3, 3, 3, color)

        then:
        1 * lanternaIO.drawPixel(0, 3, color)

        1 * lanternaIO.drawPixel(1, 1, color)
        1 * lanternaIO.drawPixel(1, 2, color)
        1 * lanternaIO.drawPixel(1, 3, color)
        1 * lanternaIO.drawPixel(1, 4, color)
        1 * lanternaIO.drawPixel(1, 5, color)

        1 * lanternaIO.drawPixel(2, 1, color)
        1 * lanternaIO.drawPixel(2, 2, color)
        1 * lanternaIO.drawPixel(2, 3, color)
        1 * lanternaIO.drawPixel(2, 4, color)
        1 * lanternaIO.drawPixel(2, 5, color)

        1 * lanternaIO.drawPixel(3, 0, color)
        1 * lanternaIO.drawPixel(3, 1, color)
        1 * lanternaIO.drawPixel(3, 2, color)
        1 * lanternaIO.drawPixel(3, 3, color)
        1 * lanternaIO.drawPixel(3, 4, color)
        1 * lanternaIO.drawPixel(3, 5, color)
        1 * lanternaIO.drawPixel(3, 6, color)

        1 * lanternaIO.drawPixel(4, 1, color)
        1 * lanternaIO.drawPixel(4, 2, color)
        1 * lanternaIO.drawPixel(4, 3, color)
        1 * lanternaIO.drawPixel(4, 4, color)
        1 * lanternaIO.drawPixel(4, 5, color)

        1 * lanternaIO.drawPixel(5, 1, color)
        1 * lanternaIO.drawPixel(5, 2, color)
        1 * lanternaIO.drawPixel(5, 3, color)
        1 * lanternaIO.drawPixel(5, 4, color)
        1 * lanternaIO.drawPixel(5, 5, color)

        1 * lanternaIO.drawPixel(6, 3, color)

        0 * lanternaIO.drawPixel(_, _, _)
    }

    def "drawCircle test with radius 4"() {
        given:
        def color = new Color("#FF0000")

        when:
        lanternaIO.drawCircle(3, 3, 3, color)

        then:
        1 * lanternaIO.drawPixel(0, 3, color)

        1 * lanternaIO.drawPixel(1, 1, color)
        1 * lanternaIO.drawPixel(1, 2, color)
        1 * lanternaIO.drawPixel(1, 3, color)
        1 * lanternaIO.drawPixel(1, 4, color)
        1 * lanternaIO.drawPixel(1, 5, color)

        1 * lanternaIO.drawPixel(2, 1, color)
        1 * lanternaIO.drawPixel(2, 2, color)
        1 * lanternaIO.drawPixel(2, 3, color)
        1 * lanternaIO.drawPixel(2, 4, color)
        1 * lanternaIO.drawPixel(2, 5, color)

        1 * lanternaIO.drawPixel(3, 0, color)
        1 * lanternaIO.drawPixel(3, 1, color)
        1 * lanternaIO.drawPixel(3, 2, color)
        1 * lanternaIO.drawPixel(3, 3, color)
        1 * lanternaIO.drawPixel(3, 4, color)
        1 * lanternaIO.drawPixel(3, 5, color)
        1 * lanternaIO.drawPixel(3, 6, color)

        1 * lanternaIO.drawPixel(4, 1, color)
        1 * lanternaIO.drawPixel(4, 2, color)
        1 * lanternaIO.drawPixel(4, 3, color)
        1 * lanternaIO.drawPixel(4, 4, color)
        1 * lanternaIO.drawPixel(4, 5, color)

        1 * lanternaIO.drawPixel(5, 1, color)
        1 * lanternaIO.drawPixel(5, 2, color)
        1 * lanternaIO.drawPixel(5, 3, color)
        1 * lanternaIO.drawPixel(5, 4, color)
        1 * lanternaIO.drawPixel(5, 5, color)

        1 * lanternaIO.drawPixel(6, 3, color)

        0 * lanternaIO.drawPixel(_, _, _)
    }

    def "drawCircle test with radius 5"() {
        given:
        def color = new Color("#FF0000")

        when:
        lanternaIO.drawCircle(5, 5, 5, color)

        then:
        1 * lanternaIO.drawPixel(0, 5, color)

        1 * lanternaIO.drawPixel(1, 2, color)
        1 * lanternaIO.drawPixel(1, 3, color)
        1 * lanternaIO.drawPixel(1, 4, color)
        1 * lanternaIO.drawPixel(1, 5, color)
        1 * lanternaIO.drawPixel(1, 6, color)
        1 * lanternaIO.drawPixel(1, 7, color)
        1 * lanternaIO.drawPixel(1, 8, color)

        1 * lanternaIO.drawPixel(2, 1, color)
        1 * lanternaIO.drawPixel(2, 2, color)
        1 * lanternaIO.drawPixel(2, 3, color)
        1 * lanternaIO.drawPixel(2, 4, color)
        1 * lanternaIO.drawPixel(2, 5, color)
        1 * lanternaIO.drawPixel(2, 6, color)
        1 * lanternaIO.drawPixel(2, 7, color)
        1 * lanternaIO.drawPixel(2, 8, color)
        1 * lanternaIO.drawPixel(2, 9, color)

        1 * lanternaIO.drawPixel(3, 1, color)
        1 * lanternaIO.drawPixel(3, 2, color)
        1 * lanternaIO.drawPixel(3, 3, color)
        1 * lanternaIO.drawPixel(3, 4, color)
        1 * lanternaIO.drawPixel(3, 5, color)
        1 * lanternaIO.drawPixel(3, 6, color)
        1 * lanternaIO.drawPixel(3, 7, color)
        1 * lanternaIO.drawPixel(3, 8, color)
        1 * lanternaIO.drawPixel(3, 9, color)

        1 * lanternaIO.drawPixel(4, 1, color)
        1 * lanternaIO.drawPixel(4, 2, color)
        1 * lanternaIO.drawPixel(4, 3, color)
        1 * lanternaIO.drawPixel(4, 4, color)
        1 * lanternaIO.drawPixel(4, 5, color)
        1 * lanternaIO.drawPixel(4, 6, color)
        1 * lanternaIO.drawPixel(4, 7, color)
        1 * lanternaIO.drawPixel(4, 8, color)
        1 * lanternaIO.drawPixel(4, 9, color)

        1 * lanternaIO.drawPixel(5, 0, color)
        1 * lanternaIO.drawPixel(5, 1, color)
        1 * lanternaIO.drawPixel(5, 2, color)
        1 * lanternaIO.drawPixel(5, 3, color)
        1 * lanternaIO.drawPixel(5, 4, color)
        1 * lanternaIO.drawPixel(5, 5, color)
        1 * lanternaIO.drawPixel(5, 6, color)
        1 * lanternaIO.drawPixel(5, 7, color)
        1 * lanternaIO.drawPixel(5, 8, color)
        1 * lanternaIO.drawPixel(5, 9, color)
        1 * lanternaIO.drawPixel(5, 10, color)


        1 * lanternaIO.drawPixel(6, 1, color)
        1 * lanternaIO.drawPixel(6, 2, color)
        1 * lanternaIO.drawPixel(6, 3, color)
        1 * lanternaIO.drawPixel(6, 4, color)
        1 * lanternaIO.drawPixel(6, 5, color)
        1 * lanternaIO.drawPixel(6, 6, color)
        1 * lanternaIO.drawPixel(6, 7, color)
        1 * lanternaIO.drawPixel(6, 8, color)
        1 * lanternaIO.drawPixel(6, 9, color)

        1 * lanternaIO.drawPixel(7, 1, color)
        1 * lanternaIO.drawPixel(7, 2, color)
        1 * lanternaIO.drawPixel(7, 3, color)
        1 * lanternaIO.drawPixel(7, 4, color)
        1 * lanternaIO.drawPixel(7, 5, color)
        1 * lanternaIO.drawPixel(7, 6, color)
        1 * lanternaIO.drawPixel(7, 7, color)
        1 * lanternaIO.drawPixel(7, 8, color)
        1 * lanternaIO.drawPixel(7, 9, color)


        1 * lanternaIO.drawPixel(8, 1, color)
        1 * lanternaIO.drawPixel(8, 2, color)
        1 * lanternaIO.drawPixel(8, 3, color)
        1 * lanternaIO.drawPixel(8, 4, color)
        1 * lanternaIO.drawPixel(8, 5, color)
        1 * lanternaIO.drawPixel(8, 6, color)
        1 * lanternaIO.drawPixel(8, 7, color)
        1 * lanternaIO.drawPixel(8, 8, color)
        1 * lanternaIO.drawPixel(8, 9, color)


        1 * lanternaIO.drawPixel(9, 2, color)
        1 * lanternaIO.drawPixel(9, 3, color)
        1 * lanternaIO.drawPixel(9, 4, color)
        1 * lanternaIO.drawPixel(9, 5, color)
        1 * lanternaIO.drawPixel(9, 6, color)
        1 * lanternaIO.drawPixel(9, 7, color)
        1 * lanternaIO.drawPixel(9, 8, color)

        1 * lanternaIO.drawPixel(10, 5, color)

        0 * lanternaIO.drawPixel(_, _, _)
    }

}
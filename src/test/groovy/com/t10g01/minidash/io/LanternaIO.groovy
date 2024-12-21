package com.t10g01.minidash.io

import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.t10g01.minidash.utils.Color
import spock.lang.Specification
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.graphics.TextGraphics

class LanternaIOSpec extends Specification {
    def "isPressed test"() {
        given:
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), Mock(Screen), Mock(TextGraphics), 0, 0, new Color("#000000"))

        expect:
        !lanternaIOAdapter.isPressed(' ' as char)
        !lanternaIOAdapter.isPressed('e' as char)

        when:
        lanternaIOAdapter.keyPressed(' ' as char)

        then:
        lanternaIOAdapter.isPressed(' ' as char)
        !lanternaIOAdapter.isPressed('e' as char)

        when:
        lanternaIOAdapter.keyReleased(' ' as char)

        then:
        !lanternaIOAdapter.isPressed(' ' as char)
        !lanternaIOAdapter.isPressed('e' as char)
    }

    def "clear test"() {
        given:
        def screen = Mock(Screen)
        def graphics = Mock(TextGraphics)
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), screen, graphics, 0, 0, new Color("#000000"))

        when:
        lanternaIOAdapter.clear()

        then:
        1 * screen.clear()
        1 * graphics.setBackgroundColor(_)
        1 * graphics.fill(Character.valueOf(' ' as char))
    }

    def "close test"() {
        given:
        def screen = Mock(Screen)
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), screen, Mock(TextGraphics), 0, 0, new Color("#000000"))

        when:
        lanternaIOAdapter.close()

        then:
        1 * screen.close()
    }

    def "refresh test"() {
        given:
        def screen = Mock(Screen);
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), screen, Mock(TextGraphics), 0, 0, new Color("#000000"))

        when:
        lanternaIOAdapter.refresh()

        then:
        1 * screen.refresh()
    }

    def "drawPixel test"(x, y, xf, yf, color, colorf) {
        given:
        def graphics = Mock(TextGraphics)
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), Mock(Screen), graphics, 10, 10, new Color("#000000"))

        when:
        lanternaIOAdapter.drawPixel(x, y, color)

        then:
        1 * graphics.setBackgroundColor(colorf)
        1 * graphics.fillRectangle(new TerminalPosition(xf, yf), new TerminalSize(1, 1), Character.valueOf(' ' as char))

        where:
        x | y | xf | yf | color                | colorf
        1 | 1 | 1  | 8  | new Color("#000000") | new TextColor.RGB(0, 0, 0)
        5 | 6 | 5  | 3  | new Color("#FFFFFF") | new TextColor.RGB(255, 255, 255)
        3 | 9 | 3  | 0  | new Color("#FF0000") | new TextColor.RGB(255, 0, 0)
    }

    def "drawRectangle test"(height, width, x, y, xf, yf, color, colorf) {
        given:
        def graphics = Mock(TextGraphics)
        def lanternaIOAdapter = new LanternaIO(Mock(Terminal), Mock(Screen), graphics, 10, 10, new Color("#000000"))

        when:
        lanternaIOAdapter.drawRectangle(x, y, width, height, color)

        then:
        1 * graphics.setBackgroundColor(colorf)
        1 * graphics.fillRectangle(new TerminalPosition(xf, yf), new TerminalSize(width, height), Character.valueOf(' ' as char))

        where:
        height | width | x | y | xf | yf | color                | colorf
        2      | 3     | 1 | 1 | 1  | 7  | new Color("#000000") | new TextColor.RGB(0, 0, 0)
        4      | 4     | 5 | 5 | 5  | 1  | new Color("#FFFFFF") | new TextColor.RGB(255, 255, 255)
        6      | 2     | 3 | 2 | 3  | 2  | new Color("#FF0000") | new TextColor.RGB(255, 0, 0)
    }

}
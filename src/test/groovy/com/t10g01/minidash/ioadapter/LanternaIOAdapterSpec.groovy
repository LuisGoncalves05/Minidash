package com.t10g01.minidash.ioadapter

import spock.lang.Specification
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.graphics.TextGraphics

import java.awt.event.KeyEvent;

class LanternaIOAdapterSpec extends Specification {
    def "isPressed handles key presses"() {
        given:
        def lanternaIOAdapter = new LanternaIOAdapter(Mock(Terminal), Mock(Screen), Mock(TextGraphics))

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
}
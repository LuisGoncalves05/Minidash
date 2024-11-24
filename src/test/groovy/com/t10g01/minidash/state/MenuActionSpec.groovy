package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.MenuAction
import spock.lang.Specification

class MenuActionSpec extends Specification {

    static IOAdapter ioAdapter
    static GameSettings gameSettings
    static Game game
    static MenuState menuState

    def setup() {
        ioAdapter = Mock(IOAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, ioAdapter, null)
        menuState = new MenuState(game, ioAdapter, gameSettings)
    }

    def "goes up when w pressed" () {
        given:
        ioAdapter.isPressed('w') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes up when k pressed" () {
        given:
        ioAdapter.isPressed('k') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes down when s pressed" () {
        given:
        ioAdapter.isPressed('s') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "goes down when j pressed" () {
        given:
        ioAdapter.isPressed('j') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "selects when space pressed" () {
        given:
        ioAdapter.isPressed(' ') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.SELECT
    }

    def "exits if q pressed" () {
        given:
        ioAdapter.isPressed('q') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.EXIT
    }

    def "does nothing when random keys pressed pressed" () {
        given:
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.NULL
    }
}

package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.MenuAction
import spock.lang.Shared
import spock.lang.Specification

class MenuActionSpec extends Specification {
    @Shared
    IOAdapter ioAdapter
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    MenuState menuState

    def setup() {
        ioAdapter = Mock(IOAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, ioAdapter, null)
        menuState = new MainMenuState(game)
    }

    def "goes up when w pressed" () {
        given:
        ioAdapter.isPressed('w' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes up when k pressed" () {
        given:
        ioAdapter.isPressed('k' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes down when s pressed" () {
        given:
        ioAdapter.isPressed('s' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "goes down when j pressed" () {
        given:
        ioAdapter.isPressed('j' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "selects when space pressed" () {
        given:
        ioAdapter.isPressed(' ' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.SELECT
    }

    def "exits if q pressed" () {
        given:
        ioAdapter.isPressed('q' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.EXIT
    }

    def "does nothing when random keys pressed pressed" () {
        given:
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.NULL
    }
}

package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.InputAdapter
import com.t10g01.minidash.ioadapter.InputAdapter
import com.t10g01.minidash.ioadapter.OutputAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.MenuAction
import spock.lang.Shared
import spock.lang.Specification

class MenuActionSpec extends Specification {

    @Shared
    InputAdapter inputAdapter
    @Shared
    OutputAdapter outputAdapter
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    MenuState menuState

    def setup() {
        inputAdapter = Mock(InputAdapter)
        outputAdapter = Mock(OutputAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, inputAdapter, outputAdapter, null)
        menuState = new MainMenuState(game)
    }

    def "goes up when w pressed" () {
        given:
        inputAdapter.isPressed('w') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes up when k pressed" () {
        given:
        inputAdapter.isPressed('k') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes down when s pressed" () {
        given:
        inputAdapter.isPressed('s') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "goes down when j pressed" () {
        given:
        inputAdapter.isPressed('j') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "selects when space pressed" () {
        given:
        inputAdapter.isPressed(' ') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.SELECT
    }

    def "exits if q pressed" () {
        given:
        inputAdapter.isPressed('q') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.EXIT
    }

    def "does nothing when random keys pressed pressed" () {
        given:
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.NULL
    }
}

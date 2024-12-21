package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.io.Input
import com.t10g01.minidash.io.Output
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.MenuAction
import spock.lang.Shared
import spock.lang.Specification

class MenuActionSpec extends Specification {

    @Shared
    Input input
    @Shared
    Output output
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    MenuState menuState

    def setup() {
        input = Mock(Input)
        output = Mock(Output)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, input, output, null)
        menuState = new MainMenuState(game)
    }

    def "goes up when w pressed" () {
        given:
        input.isPressed('w') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes up when k pressed" () {
        given:
        input.isPressed('k') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes down when s pressed" () {
        given:
        input.isPressed('s') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "goes down when j pressed" () {
        given:
        input.isPressed('j') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "selects when space pressed" () {
        given:
        input.isPressed(' ') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.SELECT
    }

    def "exits if q pressed" () {
        given:
        input.isPressed('q') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.EXIT
    }

    def "does nothing when random keys pressed pressed" () {
        given:
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        menuState.getAction() == MenuAction.NULL
    }
}

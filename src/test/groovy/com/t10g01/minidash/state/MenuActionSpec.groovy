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

        input.isPressed('w' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes up when k pressed" () {
        given:
        input.isPressed('k' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.UP
    }

    def "goes down when s pressed" () {
        given:
        input.isPressed('s' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "goes down when j pressed" () {
        given:

        input.isPressed('j' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.DOWN
    }

    def "selects when space pressed" () {
        given:
        input.isPressed(' ' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.SELECT
    }

    def "exits if q pressed" () {
        given:
        input.isPressed('q' as Character) >> true
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.EXIT
    }

    def "does nothing when random keys pressed pressed" () {
        given:
        input.isPressed('e' as Character) >> true
        input.isPressed('x' as Character) >> true
        input.isPressed('b' as Character) >> true
        input.isPressed('a' as Character) >> true

        expect:
        menuState.getAction() == MenuAction.NULL
    }
}

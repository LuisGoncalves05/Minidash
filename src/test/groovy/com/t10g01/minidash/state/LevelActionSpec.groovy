package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.io.Input
import com.t10g01.minidash.io.Output
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Shared
import spock.lang.Specification

class LevelActionSpec extends Specification{

    @Shared
    Input input
    @Shared
    Output output
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    LevelState levelState


    def setup() {
        input = Mock(Input)
        output = Mock(Output)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, input, output, null)
        levelState = new LevelState(game)
    }


    def "jumps when space pressed"() {
        given:
        input.isPressed(' ') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.JUMP
    }

    def "does nothing if random keys pressed"() {
        given:
        input.isPressed('w') >> true
        input.isPressed('e') >> true
        input.isPressed('x') >> true
        input.isPressed('b') >> true
        input.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.NULL
    }

    def "exits if q pressed" (){
        given:
        input.isPressed('q') >> true

        expect:
        levelState.getAction() == LevelAction.EXIT
    }
}

package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.InputAdapter
import com.t10g01.minidash.ioadapter.OutputAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Shared
import spock.lang.Specification

class LevelActionSpec extends Specification{

    @Shared
    InputAdapter inputAdapter
    @Shared
    OutputAdapter outputAdapter
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    LevelState levelState


    def setup() {
        inputAdapter = Mock(InputAdapter)
        outputAdapter = Mock(OutputAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, inputAdapter, outputAdapter, null)
        levelState = new LevelState(game)
    }


    def "jumps when space pressed"() {
        given:
        inputAdapter.isPressed(' ') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.JUMP
    }

    def "does nothing if random keys pressed"() {
        given:
        inputAdapter.isPressed('w') >> true
        inputAdapter.isPressed('e') >> true
        inputAdapter.isPressed('x') >> true
        inputAdapter.isPressed('b') >> true
        inputAdapter.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.NULL
    }

    def "exits if q pressed" (){
        given:
        inputAdapter.isPressed('q') >> true

        expect:
        levelState.getAction() == LevelAction.EXIT
    }
}

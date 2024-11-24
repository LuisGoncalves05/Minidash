package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Specification

class LevelActionSpec extends Specification{

    static IOAdapter ioAdapter
    static GameSettings gameSettings
    static Game game
    static LevelState levelState


    def setup() {
        ioAdapter = Mock(IOAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, ioAdapter, null)
        levelState = new LevelState(game, ioAdapter, gameSettings)
    }


    def "jumps when space pressed" (){
        given:
        ioAdapter.isPressed(' ') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.JUMP
    }

    def "does nothing if random keys pressed" (){
        given:
        ioAdapter.isPressed('w') >> true
        ioAdapter.isPressed('e') >> true
        ioAdapter.isPressed('x') >> true
        ioAdapter.isPressed('b') >> true
        ioAdapter.isPressed('a') >> true

        expect:
        levelState.getAction() == LevelAction.NULL
    }

    def "exits if q pressed" (){
        given:
        ioAdapter.isPressed('q') >> true

        expect:
        levelState.getAction() == LevelAction.EXIT
    }
}

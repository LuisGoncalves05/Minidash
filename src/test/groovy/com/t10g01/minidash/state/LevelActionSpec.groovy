package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Shared
import spock.lang.Specification

class LevelActionSpec extends Specification{
    @Shared
    IOAdapter ioAdapter
    @Shared
    GameSettings gameSettings
    @Shared
    Game game
    @Shared
    LevelState levelState


    def setup() {
        ioAdapter = Mock(IOAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, ioAdapter, null)
        levelState = new LevelState(game, -1)
    }


    def "jumps when space pressed"() {
        given:
        ioAdapter.isPressed(' ' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        levelState.getAction() == LevelAction.JUMP
    }

    def "does nothing if random keys pressed" (){
        given:
        ioAdapter.isPressed('w' as Character) >> true
        ioAdapter.isPressed('e' as Character) >> true
        ioAdapter.isPressed('x' as Character) >> true
        ioAdapter.isPressed('b' as Character) >> true
        ioAdapter.isPressed('a' as Character) >> true

        expect:
        levelState.getAction() == LevelAction.NULL
    }

    def "exits if q pressed" (){
        given:
        ioAdapter.isPressed('q' as Character) >> true

        expect:
        levelState.getAction() == LevelAction.EXIT
    }
}

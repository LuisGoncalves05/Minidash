package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class LevelStateSpec extends Specification {
    def setup() {
        ioAdapter = Mock(IOAdapter)
        gameSettings = Mock(GameSettings)
        game = new Game(gameSettings, ioAdapter, null)
        levelState = new LevelState(game, ioAdapter, gameSettings, 0)
    }
}

package com.t10g01.minidash

import com.t10g01.minidash.ioadapter.InputAdapter
import com.t10g01.minidash.ioadapter.OutputAdapter
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class GameSpec extends Specification{

    static GameSettings gameSettings
    static InputAdapter inputAdapter
    static OutputAdapter outputAdapter

    def setup()  {
        gameSettings = Mock(GameSettings)
        inputAdapter = Mock(InputAdapter)
        outputAdapter = Mock(OutputAdapter)
    }

    def "closes when state is null"() {
        given:
        def game = new Game(gameSettings, inputAdapter, outputAdapter, null)

        when:
        game.start()

        then:
        1 * outputAdapter.close()
        0 * _
    }
}

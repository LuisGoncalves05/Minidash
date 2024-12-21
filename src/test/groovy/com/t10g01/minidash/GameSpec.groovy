package com.t10g01.minidash

import com.t10g01.minidash.io.Input
import com.t10g01.minidash.io.Output
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class GameSpec extends Specification{

    static GameSettings gameSettings
    static Input input
    static Output output

    def setup()  {
        gameSettings = Mock(GameSettings)
        input = Mock(Input)
        output = Mock(Output)
    }

    def "closes when state is null"() {
        given:
        def game = new Game(gameSettings, input, output, null)

        when:
        game.start()

        then:
        1 * output.close()
        0 * _
    }
}

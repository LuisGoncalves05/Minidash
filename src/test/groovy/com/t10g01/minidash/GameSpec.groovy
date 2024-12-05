package com.t10g01.minidash

import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class GameSpec extends Specification{

    static GameSettings gameSettings
    static IOAdapter ioAdapter

    def setup()  {
        gameSettings = Mock(GameSettings)
        ioAdapter = Mock(IOAdapter)
    }

    def "closes when state is null" (){
        given:
        def game = new Game(gameSettings, ioAdapter, null)

        when:
        game.start()

        then:
        1 * ioAdapter.close()
        0 * _
    }
}

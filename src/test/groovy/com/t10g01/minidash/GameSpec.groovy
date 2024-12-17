package com.t10g01.minidash

import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.state.State
import spock.lang.Specification

class GameSpec extends Specification{
    def "closes when state is null"() {
        given:
        def gameSettings = Mock(GameSettings)
        def ioAdapter = Mock(IOAdapter)
        def game = new Game(gameSettings, ioAdapter, null)

        when:
        game.start()

        then:
        1 * ioAdapter.close()
        0 * _
    }
}

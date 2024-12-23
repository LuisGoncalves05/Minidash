package com.t10g01.minidash.state

import com.t10g01.minidash.Game
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Boost
import com.t10g01.minidash.model.LevelEnd
import com.t10g01.minidash.model.DoubleJump
import com.t10g01.minidash.model.Platform
import com.t10g01.minidash.model.ReversedSpike
import com.t10g01.minidash.model.Spike
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class LevelStateSpec extends Specification {

    def "parses level correctly"() {
        when:
        def game = Mock(Game)
        def settings = Mock(GameSettings)
        settings.getRotationSpeed() >> 0
        game.getGameSettings() >> settings
        def levelState = new LevelState(game)

        then:
        levelState.model.elements == [new Platform(0, 0), new Boost(0, 1), new ReversedSpike(1, 0), new Spike(1, 1), new DoubleJump(1, 2)]
    }

    def "parses big string correctly"() {
        expect:
            LevelState.getElement(str, x, y) == object;
        where:
            str           | x  | y  | object
            "#| > #>)*<_" | 1  | 0  | new Block(1, 0)
            "#| > #>)*<_" | 10 | 1  | new Platform(10, 1)
            "#| > #>)*<_" | 0  | 2  | null
            "#| > #>)*<_" | 1  | 3  | new Spike(1, 3)
            "#| > #>)*<_" | 2  | 4  | null
            "#| > #>)*<_" | 3  | 5  | new Block(3,5)
            "#| > #>)*<_" | 42 | 6  | new Spike(42, 6)
            "#| > #>)*<_" | 2  | 7  | new Boost(2, 7)
            "#| > #>)*<_" | 45 | 8  | new DoubleJump(45, 8)
            "#| > #>)*<_" | 78 | 9  | new ReversedSpike(78, 9)
            "#| > #>)*<_" | 99 | 10 | new LevelEnd(99, 10)
    }

    def "parses big string correctly"() {
        when:
        LevelState.getElement(str, x, y)

        then:
        thrown(IllegalArgumentException)

        where:
        str   | x  | y
        "q1+" | 1  | 0
        "q1+" | 42 | 1
        "q1+" | 99 | 2

    }

    def "throws in null string"() {
        when:
            LevelState.getElement(null, 1, 1)
        then:
            thrown(NullPointerException)
    }

    def "throws in out of bounds index"() {
        when:
            LevelState.getElement("#", x, y)
        then:
            thrown(IndexOutOfBoundsException)
        where:
            y  | x
            42 | 1
            1  | 2
            -1 | 9
            -42| 42
    }
}

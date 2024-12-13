package com.t10g01.minidash.state

import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Platform
import com.t10g01.minidash.model.Spike
import spock.lang.Specification

class LevelStateSpec extends Specification {
    def "parses big string correctly"() {
        expect:
            LevelState.getElement(str, x, y).equals(object);
        where:
            str         | x  | y | object
            "#| >1h #>" | 1  | 0 | new Block(1, 0)
            "#| >1h #>" | 10 | 1 | new Platform(10, 1)
            "#| >1h #>" | 0  | 2 | null
            "#| >1h #>" | 1  | 3 | new Spike(1, 3)
            "#| >1h #>" | 20 | 4 | null
            "#| >1h #>" | 1  | 5 | null
            "#| >1h #>" | 2  | 6 | null
            "#| >1h #>" | 3  | 7 | new Block(3,7)
            "#| >1h #>" | 42 | 8 | new Spike(42, 8)
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

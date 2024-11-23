package com.t10g01.minidash.view

import com.t10g01.minidash.ioadapter.*
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Position
import com.t10g01.minidash.utils.*
import com.t10g01.minidash.model.LevelModel
import spock.lang.Specification

class LevelViewSpec extends Specification {

    static LevelModel model
    static IOAdapter ioAdapter
    static GameSettings settings
    static Player player

    def setup() {
        model = Mock(LevelModel)
        player = Mock(Player)
        model.getPlayer() >> player

        ioAdapter = Mock(IOAdapter)

        settings = Mock(GameSettings)
        settings.getResolution() >> 10
        settings.getCameraWidth() >> 10
        settings.getCameraHeight() >> 5

    }

    def "block visitor"(x, y, xp, yp, xf, yf, width, height) {
        given:
        def block = Mock(Block)
        def blockPosition = Mock(Position)
        block.getPosition() >> blockPosition
        blockPosition.getX() >> x
        blockPosition.getY() >> y
        def blockColorMock = Mock(Color)
        settings.getBlockColor() >> blockColorMock

        def playerPosition = Mock(Position)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.visitBlock(block)

        then:
        1 * ioAdapter.drawRectangle(xf, yf, width, height, blockColorMock)

        where:
        x  | y  | xp   | yp  | xf  | yf  | width | height
        0  | 0  | 0    | 1   | 40  | 0   | 10    | 10
        0  | 0  | 4    | 1   | 0   | 0   | 10    | 10
        5  | 3  | 0    | 1   | 90  | 30  | 10    | 10
        50 | 1  | 48   | 3   | 60  | 10  | 10    | 10
        50 | 1  | 54.7 | 1   | 0   | 10  | 3     | 10
        50 | 1  | 44.8 | 1   | 92  | 10  | 8     | 10
    }

    def "out of sight block visitor"(x, y, xp, yp) {
        given:
        def block = Mock(Block)
        def blockPosition = Mock(Position)
        block.getPosition() >> blockPosition
        blockPosition.getX() >> x
        blockPosition.getY() >> y
        def blockColorMock = Mock(Color)
        settings.getBlockColor() >> blockColorMock

        def playerPosition = Mock(Position)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.visitBlock(block)

        then:
        0 * ioAdapter.drawRectangle(_, _, _, _, _)

        where:
        x  | y  | xp | yp
        0  | 0  | 10 | 1
        50 | 0  | 10 | 1
    }
}

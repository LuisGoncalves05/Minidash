package com.t10g01.minidash.view

import com.t10g01.minidash.ioadapter.*
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Spike
import com.t10g01.minidash.model.Vector2D
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
        settings.getCameraHeight() >> 6
        settings.getCameraCutoff() >> 0.5

    }

    def "block visitor"(x, y, xp, yp, xf, yf, width, height) {
        given:
        def block = Mock(Block)
        def blockPosition = Mock(Vector2D)
        block.getPosition() >> blockPosition
        blockPosition.getX() >> x
        blockPosition.getY() >> y
        def blockColorMock = Mock(Color)
        settings.getBlockColor() >> blockColorMock

        def playerPosition = Mock(Vector2D)
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
        0  | 1  | 0    | 4   | 40  | 0   | 10    | 10
        0  | 0  | 0    | 3.5 | 40  | 0   | 10    | 5
        0  | 3  | 0    | 5   | 40  | 10  | 10    | 10
        11 | 3  | 10   | 4   | 50  | 20  | 10    | 10

    }

    def "out of sight block visitor"(x, y, xp, yp) {
        given:
        def block = Mock(Block)
        def blockPosition = Mock(Vector2D)
        block.getPosition() >> blockPosition
        blockPosition.getX() >> x
        blockPosition.getY() >> y
        def blockColorMock = Mock(Color)
        settings.getBlockColor() >> blockColorMock

        def playerPosition = Mock(Vector2D)
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
        0  | 0  | 0  | 4
        50 | 0  | 50 | 5

    }

    def "spike visitor"(x, y, xp, yp, xf, yf) {
        given:
        def spike = Mock(Spike)
        def spikePosition = Mock(Vector2D)
        spike.getPosition() >> spikePosition
        spikePosition.getX() >> x
        spikePosition.getY() >> y
        def spikeColor = Mock(Color)
        settings.getSpikeColor() >> spikeColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.visitSpike(spike)

        then:
        1 * ioAdapter.drawRectangle(xf, yf, 10, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 1, yf + 1, 8, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 2, yf + 2, 6, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 3, yf + 3, 4, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 4, yf + 4, 2, 1, spikeColor)

        where:
        x  | y  | xp   | yp  | xf  | yf
        0  | 0  | 0    | 1   | 40  | 0
        0  | 0  | 4    | 1   | 0   | 0
        0  | 1  | 4    | 4   | 0   | 0
        0  | 1  | 0    | 4   | 40  | 0
        5  | 3  | 0    | 1   | 90  | 30
        50 | 1  | 48   | 3   | 60  | 10
    }

    def "spike visitor edge case left"() {
        given:
        def spike = Mock(Spike)
        def spikePosition = Mock(Vector2D)
        spike.getPosition() >> spikePosition
        spikePosition.getX() >> 50
        spikePosition.getY() >> 1
        def spikeColor = Mock(Color)
        settings.getSpikeColor() >> spikeColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> 54.7
        playerPosition.getY() >> 1

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.visitSpike(spike)

        then:
        1 * ioAdapter.drawRectangle(0, 10, 3, 1, spikeColor)
        1 * ioAdapter.drawRectangle(0, 11, 2, 1, spikeColor)
        1 * ioAdapter.drawRectangle(0, 12, 1, 1, spikeColor)
    }

    def "spike visitor edge case right"() {
        given:
        def spike = Mock(Spike)
        def spikePosition = Mock(Vector2D)
        spike.getPosition() >> spikePosition
        spikePosition.getX() >> 50
        spikePosition.getY() >> 1
        def spikeColor = Mock(Color)
        settings.getSpikeColor() >> spikeColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> 44.8
        playerPosition.getY() >> 1

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.visitSpike(spike)

        then:
        1 * ioAdapter.drawRectangle(92, 10, 8, 1, spikeColor)
        1 * ioAdapter.drawRectangle(93, 11, 7, 1, spikeColor)
        1 * ioAdapter.drawRectangle(94, 12, 6, 1, spikeColor)
        1 * ioAdapter.drawRectangle(95, 13, 4, 1, spikeColor)
        1 * ioAdapter.drawRectangle(96, 14, 2, 1, spikeColor)
    }

    def "out of sight spike visitor"(x, y, xp, yp) {
        given:
        def spike = Mock(Spike)
        def spikePosition = Mock(Vector2D)
        spike.getPosition() >> spikePosition
        spikePosition.getX() >> x
        spikePosition.getY() >> y
        def spikeColor = Mock(Color)
        settings.getSpikeColor() >> spikeColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.visitSpike(spike)

        then:
        0 * ioAdapter.drawRectangle(_, _, _, _, _)

        where:
        x  | y  | xp | yp
        0  | 0  | 10 | 1
        50 | 0  | 10 | 1
        0  | 0  | 5  | 1
        10 | 4  | 4  | 1
        0  | 0  | 0  | 4
        10 | 4  | 10 | 8
    }


    def "drawing a player: drawing all pixels"() {
        given:

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> 0
        playerPosition.getY() >> 0
        def playerColorMock = Mock(Color)
        settings.getPlayerColor() >> playerColorMock

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.drawPlayer(model.getPlayer())

        then:
        100 * ioAdapter.drawPixel(_, _, playerColorMock)
    }

    def "drawing a player: drawing the right pixels"(xp, yp, rot, xi, yi, xf, yf) {
        given:
        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        player.getRotation() >> rot

        def playerColorMock = Mock(Color)
        settings.getPlayerColor() >> playerColorMock

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.drawPlayer(model.getPlayer())

        then:
        1 * ioAdapter.drawPixel(xf, yf, playerColorMock)

        where:
        xp | yp | rot | xi | yi | xf | yf
        0  | 0  | 0   | 0  | 0  | 40 | 0
        10 | 0  | 0   | 10 | 0  | 40 | 0
        0  | 1  | 0   | 0  | 1  | 40 | 10
        50 | 3  | 0   | 50 | 3  | 40 | 30
        50 | 4  | 0   | 50 | 3  | 40 | 30

    }

    def "drawing a player: not drawing the wrong pixels"(xp, yp, rot, xi, yi, xf, yf) {
        given:
        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        player.getRotation() >> rot

        def playerColorMock = Mock(Color)
        settings.getPlayerColor() >> playerColorMock

        when:
        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.drawPlayer(model.getPlayer())

        then:
        0 * ioAdapter.drawPixel(xi, yi, xf, yf, playerColorMock)

        where:
        xp | yp | rot | xi | yi | xf | yf
        0  | 0  | 45  | 0  | 0  | 40 | 0
        10 | 0  | 45  | 10 | 0  | 40 | 0
        0  | 1  | 135 | 0  | 1  | 40 | 10
        50 | 3  | 50  | 50 | 3  | 40 | 30
        50 | 4  | 30  | 50 | 3  | 40 | 30

    }


}

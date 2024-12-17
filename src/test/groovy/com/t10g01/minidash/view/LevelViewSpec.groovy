package com.t10g01.minidash.view

import com.t10g01.minidash.ioadapter.*
import com.t10g01.minidash.model.DoubleJump
import com.t10g01.minidash.model.Element
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Spike
import com.t10g01.minidash.model.ReversedSpike
import com.t10g01.minidash.model.Platform
import com.t10g01.minidash.model.Boost
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

    def "block visitor"(x, y, xp, yp, xf, yf, cy) {
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

        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.setCameraY(cy)

        when:
        levelView.visitBlock(block)

        then: 
        1 * ioAdapter.drawRectangle(xf, yf, 10, 10, blockColorMock)

        where:

        x  | y  | xp   | yp  | xf  | yf  | cy
        0  | 0  | 0    | 1   | 40  | 0   | 0
        0  | 0  | 4    | 1   | 0   | 0   | 0
        5  | 3  | 0    | 1   | 90  | 30  | 0
        50 | 1  | 48   | 3   | 60  | 10  | 0
        0  | 1  | 0    | 4   | 40  | 0   | 1
        0  | 3  | 0    | 5   | 40  | 10  | 2
        11 | 3  | 10   | 4   | 50  | 20  | 1

    }

    def "spike visitor"(x, y, xp, yp, xf, yf, cy) {
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
        levelView.setCameraY(cy)

        when:
        levelView.visitSpike(spike)

        then:
        1 * ioAdapter.drawRectangle(xf, yf, 10, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 1, yf + 1, 8, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 2, yf + 2, 6, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 3, yf + 3, 4, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 4, yf + 4, 2, 1, spikeColor)

        where:
        x  | y  | xp   | yp  | xf  | yf  | cy
        0  | 0  | 0    | 1   | 40  | 0   | 0
        0  | 0  | 4    | 1   | 0   | 0   | 0
        0  | 1  | 4    | 4   | 0   | 0   | 1
        0  | 1  | 0    | 4   | 40  | 0   | 1
        5  | 3  | 0    | 1   | 90  | 30  | 0
        50 | 1  | 48   | 3   | 60  | 10  | 0
    }

    def 'reversedSpike visitor'() {
        given:
        def spike = Mock(ReversedSpike)
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
        levelView.setCameraY(cy)

        when:
        levelView.visitReversedSpike(spike)

        then:
        1 * ioAdapter.drawRectangle(xf, yf + 9, 10, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 1, yf + 8, 8, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 2, yf + 7, 6, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 3, yf + 6, 4, 1, spikeColor)
        1 * ioAdapter.drawRectangle(xf + 4, yf + 5, 2, 1, spikeColor)

        where:
        x  | y  | xp   | yp  | xf  | yf  | cy
        0  | 0  | 0    | 1   | 40  | 0   | 0
        0  | 0  | 4    | 1   | 0   | 0   | 0
        0  | 1  | 4    | 4   | 0   | 0   | 1
        0  | 1  | 0    | 4   | 40  | 0   | 1
        5  | 3  | 0    | 1   | 90  | 30  | 0
        50 | 1  | 48   | 3   | 60  | 10  | 0
    }

    def "platform visitor"() {
        given:
        def platform = Mock(Platform)
        def platformPosition = Mock(Vector2D)
        platform.getPosition() >> platformPosition
        platformPosition.getX() >> x
        platformPosition.getY() >> y
        def platformColor = Mock(Color)
        settings.getPlatformColor() >> platformColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.setCameraY(cy)

        when:
        levelView.visitPlatform(platform)

        then:
        1 * ioAdapter.drawRectangle(xf, yf + 7, 10, 3, platformColor)

        where:
        x  | y  | xp   | yp  | xf  | yf | cy
        0  | 0  | 0    | 1   | 40  | 0  | 0
        0  | 0  | 4    | 1   | 0   | 0  | 0
        5  | 3  | 0    | 1   | 90  | 30 | 0
        50 | 1  | 48   | 3   | 60  | 10 | 0
        50 | 1  | 48   | 4   | 60  | 0  | 1
    }

    def 'boost visitor'(x, y, xp, yp, cy) {
        given:
        def boost = Mock(Boost)
        def boostPosition = Mock(Vector2D)
        boost.getPosition() >> boostPosition
        boostPosition.getX() >> x
        boostPosition.getY() >> y
        def boostColor = Mock(Color)
        settings.getBoostColor() >> boostColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.setCameraY(cy)

        when:
        levelView.visitBoost(boost)

        then:
        1 * ioAdapter.drawRectangle(xf + 1, yf, 8, 1, boostColor)
        1 * ioAdapter.drawRectangle(xf + 2, yf + 1, 6, 1, boostColor)

        where:
        x  | y  | xp   | yp  | xf  | yf | cy
        0  | 0  | 0    | 1   | 40  | 0  | 0
        0  | 0  | 4    | 1   | 0   | 0  | 0
        0  | 1  | 4    | 4   | 0   | 0  | 1
        0  | 1  | 0    | 4   | 40  | 0  | 1
        5  | 3  | 0    | 1   | 90  | 30 | 0
        50 | 1  | 48   | 3   | 60  | 10 | 0
    }

    def 'doubleJump visitor'() {
        given:
        def doubleJump = Mock(DoubleJump)
        def doubleJumpPosition = Mock(Vector2D)
        doubleJump.getPosition() >> doubleJumpPosition
        doubleJumpPosition.getX() >> x
        doubleJumpPosition.getY() >> y
        def doubleJumpColor = Mock(Color)
        settings.getDoubleJumpColor() >> doubleJumpColor

        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        def levelView = new LevelView(model, ioAdapter, settings)
        levelView.setCameraY(cy)

        when:
        levelView.visitDoubleJump(doubleJump)

        then:
        1 * ioAdapter.drawCircle(xf, yf, 2, doubleJumpColor)

        where:
        x  | y  | xp | yp  | xf  | yf | cy
        0  | 0  | 0  | 1   | 44  | 4  | 0
    }

    def "drawing a player: drawing the right pixels"(xp, yp, rot, xf, yf) {
        given:
        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        player.getRotation() >> rot

        def playerColorMock = Mock(Color)
        settings.getPlayerColor() >> playerColorMock

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.drawPlayer(model.getPlayer())

        then:
        1 * ioAdapter.drawPixel(xf, yf, playerColorMock)

        where:
        xp | yp | rot | xf | yf
        0  | 0  | 0   | 40 | 0
        10 | 0  | 0   | 40 | 0
        0  | 1  | 0   | 40 | 10
        50 | 3  | 0   | 40 | 30
        50 | 4  | 0   | 40 | 30
        1  | 1  | 45  | 38 | 15
        1  | 1  | 45  | 52 | 15
        1  | 1  | 45  | 45 | 22
        1  | 1  | 45  | 45 | 18
    }

    def "drawing a player: not drawing the wrong pixels"(xp, yp, rot, xf, yf) {
        given:
        def playerPosition = Mock(Vector2D)
        player.getPosition() >> playerPosition
        playerPosition.getX() >> xp
        playerPosition.getY() >> yp

        player.getRotation() >> rot

        def playerColorMock = Mock(Color)
        settings.getPlayerColor() >> playerColorMock

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.drawPlayer(model.getPlayer())

        then:
        0 * ioAdapter.drawPixel(xi, yi, xf, yf, playerColorMock)

        where:
        xp | yp | rot | xf | yf
        0  | 0  | 45  | 40 | 0
        10 | 0  | 45  | 40 | 0
        0  | 1  | 135 | 40 | 10
        50 | 3  | 50  | 40 | 30
        50 | 4  | 30  | 40 | 30
    }

    def "update pointers"() {
        given:
        def element1 = Mock(Element)
        def position1 = new Vector2D(1, 0)
        element1.getPosition() >> position1
        def element2 = Mock(Element)
        def position2 = new Vector2D(2, 0)
        element2.getPosition() >> position2
        def element3 = Mock(Element)
        def position3 = new Vector2D(10, 0)
        element3.getPosition() >> position3
        def element4 = Mock(Element)
        def position4 = new Vector2D(15, 0)
        element4.getPosition() >> position4

        model.getElements() >> Arrays.asList(element1, element2, element3, element4)

        def playerPosition = new Vector2D(playerX, 0)
        player.getPosition() >> playerPosition

        def levelView = new LevelView(model, ioAdapter, settings)

        when:
        levelView.updatePointers()

        then:
        levelView.getLeftPointer() == left
        levelView.getRightPointer() == right

        where:
        playerX | left | right
        4       | 0    | 2
        5       | 0    | 3
        6       | 1    | 3
        6.5     | 1    | 3
        5.5     | 0    | 3
        10       | 2    | 4
    }
}

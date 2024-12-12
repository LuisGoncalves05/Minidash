package com.t10g01.minidash.controller

import com.t10g01.minidash.Game
import com.t10g01.minidash.model.Element
import com.t10g01.minidash.model.LevelModel
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Spike
import com.t10g01.minidash.model.Platform
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Vector2D
import com.t10g01.minidash.state.MenuState
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Specification

class LevelControllerSpec extends Specification {
    LevelModel model
    Game game
    Player player
    PlayerController playerController
    LevelController levelController

    def setup() {
        model = Mock(LevelModel)
        model.elements() >> new ArrayList<Element>()
        player = Mock(Player)
        player.getPosition() >> new Vector2D(0, 0)
        model.player() >> player
        game = Mock(Game)
        playerController = Mock(PlayerController)
        levelController = new LevelController(model, game, playerController)
    }

    def "step updates player"(dt) {
        given:
        LevelAction action = LevelAction.NULL

        when:
        levelController.step(action, dt)

        then:
        1 * playerController.update(dt)

        where:
        dt | _
        1  | _
        2  | _
    }

    def "step makes player jump"() {
        given:
        LevelAction action = LevelAction.JUMP

        when:
        levelController.step(action, 0)

        then:
        1 * playerController.jump(_, _)
        1 * playerController.update(_)
    }

    def "step ends game on exit"() {
        given:
        LevelAction action = LevelAction.EXIT

        when:
        levelController.step(action, 0)

        then:
        1 * game.setState({it instanceof MenuState})
    }

    def "visitBlock does nothing if no collisions"() {
        given:
        def block = Mock(Block)
        block.collision(player) >> false
        block.topCollision(player) >> false

        when:
        levelController.visitBlock(block)

        then:
        0 * playerController.setGrounded(_)
        0 * game.setState(_)
    }

    def "visitBlock grounds player"(h) {
        given:
        def block = Mock(Block)
        block.topCollision(player) >> true
        def position = Mock(Vector2D)
        position.getY() >> h
        block.getPosition() >> position

        when:
        levelController.visitBlock(block)

        then:
        playerController.setGrounded(h + 1)

        where:
        h | _
        2 | _
        3 | _
    }

    def "visitBlock ends game"() {
        given:
        def block = Mock(Block)
        block.topCollision(player) >> false
        block.collision(player) >> true

        when:
        levelController.visitBlock(block)

        then:
        1 * game.setState(null)
    }

    def "visitSpike does nothing if no collisions"() {
        given:
        def spike = Mock(Spike)
        spike.collision(player) >> false

        when:
        levelController.visitSpike(spike)

        then:
        0 * playerController.setGrounded(_)
        0 * game.setState(_)
    }

    def "visitSpike ends game"() {
        given:
        def spike = Mock(Spike)
        spike.collision(player) >> true

        when:
        levelController.visitSpike(spike)

        then:
        1 * game.setState(null)
    }

    def "visitPlatform does nothing if no collisions"() {
        given:
        def platform = Mock(Platform)
        platform.collision(player) >> false
        platform.topCollision(player) >> false

        when:
        levelController.visitPlatform(platform)

        then:
        0 * playerController.setGrounded(_)
        0 * game.setState(_)
    }

    def "visitPlatform grounds player"(h) {
        given:
        def platform = Mock(Platform)
        platform.topCollision(player) >> true
        def position = Mock(Vector2D)
        position.getY() >> h
        platform.getPosition() >> position

        when:
        levelController.visitPlatform(platform)

        then:
        playerController.setGrounded(h + 1)

        where:
        h | _
        2 | _
        3 | _
    }

    def "visitPlatform ends game"() {
        given:
        def platform = Mock(Platform)
        platform.topCollision(player) >> false
        platform.collision(player) >> true

        when:
        levelController.visitPlatform(platform)

        then:
        1 * game.setState(null)
    }

    def "updatePointers"() {
        given:
        def element1 = Mock(Element)
        def position1 = new Vector2D(1, 0)
        element1.getPosition() >> position1
        def element2 = Mock(Element)
        def position2 = new Vector2D(1.5, 0)
        element2.getPosition() >> position2
        def element3 = Mock(Element)
        def position3 = new Vector2D(2, 0)
        element3.getPosition() >> position3
        def element4 = Mock(Element)
        def position4 = new Vector2D(5, 0)
        element4.getPosition() >> position4

        def model = Mock(LevelModel)
        model.elements() >> Arrays.asList(element1, element2, element3, element4)

        def player = Mock(Player)
        def playerPosition = new Vector2D(playerX, 0)
        player.getPosition() >> playerPosition
        model.player() >> player

        def levelController = new LevelController(model, game, playerController)

        when:
        levelController.updatePointers()

        then:
        levelController.getLeftPointer() == left
        levelController.getRightPointer() == right

        where:
        playerX | left | right
        0       | 0    | 1
        0.5     | 0    | 2
        1       | 0    | 3
        4       | 3    | 4
    }
}

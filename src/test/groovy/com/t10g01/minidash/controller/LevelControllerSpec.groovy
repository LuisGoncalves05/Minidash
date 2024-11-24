package com.t10g01.minidash.controller

import com.t10g01.minidash.Game
import com.t10g01.minidash.model.Collidable
import com.t10g01.minidash.model.LevelModel
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Position
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Specification

import java.util.ArrayList

class LevelControllerSpec extends Specification {
    LevelModel model
    Game game
    Player player
    PlayerController playerController
    LevelController levelController

    def setup() {
        model = Mock(LevelModel)
        model.getCollidables() >> new ArrayList<Collidable>()
        player = Mock(Player)
        model.getPlayer() >> player
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
        1 * game.setState(null)
        0 * _
    }

    def "step visits colliders"() {
        given:
        def collidable = Mock(Collidable)
        def model = Mock(LevelModel)
        model.getCollidables() >> Arrays.asList(collidable, collidable, collidable)
        def levelController = new LevelController(model, game, playerController)
        LevelAction action = LevelAction.NULL

        when:
        levelController.step(action, 0)

        then:
        3 * collidable.accept(_)
    }

    def "visitCollider does nothing if no collisions"() {
        given:
        def block = Mock(Block)
        block.collides(_) >> false
        block.topCollision(_) >> false

        when:
        levelController.visitBlock(block)

        then:
        0 * playerController.setGrounded(_)
        0 * game.setState(_)
    }

    def "visitBlock grounds player"(h) {
        given:
        def block = Mock(Block)
        block.topCollision(_) >> true
        def position = Mock(Position)
        position.getY() >> h
        block.getPosition() >> position

        when:
        levelController.visitBlock(block)

        then:
        playerController.setGrounded(h)

        where:
        h | _
        2 | _
        3 | _
    }

    def "visitBlock ends game"() {
        given:
        def block = Mock(Block)
        block.topCollision(_) >> false
        block.collides(_) >> true

        when:
        levelController.visitBlock(block)

        then:
        1 * game.setState(null)
    }
}

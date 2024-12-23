package com.t10g01.minidash.controller

import com.t10g01.minidash.Game
import com.t10g01.minidash.model.DoubleJump
import com.t10g01.minidash.model.Element
import com.t10g01.minidash.model.LevelModel
import com.t10g01.minidash.model.Block
import com.t10g01.minidash.model.LevelEnd
import com.t10g01.minidash.model.Spike
import com.t10g01.minidash.model.ReversedSpike
import com.t10g01.minidash.model.Platform
import com.t10g01.minidash.model.Boost
import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Vector2D
import com.t10g01.minidash.sound.SoundPlayer
import com.t10g01.minidash.state.MenuState
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject


class LevelControllerSpec extends Specification {
    @Shared
    LevelModel model
    @Shared
    Game game
    @Shared
    Player player
    @Shared
    PlayerController playerController
    @Shared
    GameSettings gameSettings
    @Shared
    SoundPlayer soundPlayer
    @Shared
    LevelController levelController

    def setup() {
        model = Mock(LevelModel)
        model.getElements() >> new ArrayList<Element>()
        player = Mock(Player)
        model.getPlayer() >> player
        game = Mock(Game)
        gameSettings = new GameSettings()
        game.getGameSettings() >> gameSettings
        playerController = Mock(PlayerController)
        soundPlayer = Mock(SoundPlayer)
        levelController = Spy(LevelController, constructorArgs: [model, game, playerController, soundPlayer])
    }

    def "step updates player in valid position"(double dt, LevelAction action, double px, double py) {
        given:
        player.getPosition() >> new Vector2D(px, py)

        when:
        levelController.step(action, dt)

        then:
        1 * playerController.update(dt)
        1 * levelController.updatePointers()
        1 * player.setOnDoubleJump(false)
        1 * player.setOnBoost(false)

        where:
        dt   | action           | px   | py
        1.2  | LevelAction.JUMP | 18.9 | 1
        23   | LevelAction.JUMP | 42   | 42
        0    | LevelAction.NULL | 12   | 45
        0.1  | LevelAction.NULL | 42.2 | 42
    }


    def "step makes player jump in valid position"(double dt, double px, double py) {
        given:
        player.getPosition() >> new Vector2D(px, py)

        when:
        levelController.step(LevelAction.JUMP, dt)

        then:
        1 * playerController.jump(_, _)
        1 * playerController.update(_)
        0 * game.resetState()

        where:
        dt   | px   | py
        1.2  | 18.9 | 1
        23   | 42   | 42
        0    | 12   | 45
        0.1  | 0.1  | 0.1
        0    | 0    | 0
    }

    def "step ends game on exit in valid position" (double dt, double px, double py) {
        given:
        player.getPosition() >> new Vector2D(px, py)

        when:
        levelController.step(LevelAction.EXIT, dt)

        then:
        1 * game.setState({it instanceof MenuState})
        1 * soundPlayer.stopSound()

        where:
        dt   | px   | py
        1.2  | 18.9 | 1
        23   | 42   | 42
        0    | 12   | 45
        0.1  | 42   | 245
    }

    def "step ends game on exit in invalid position"(double dt, double px, double py) {
        given:
        player.getPosition() >> new Vector2D(px, py)

        when:
        levelController.step(LevelAction.EXIT, dt)

        then:
        1 * game.setState({it instanceof MenuState})
        1 * soundPlayer.stopSound()
        0 * game.resetState()

        where:
        dt   | px    | py
        1.2  | -18.9 | 1
        23   | 42    | -2.3
        0    | -12   | -45
        0.1  | 4.90  | -42
        0.1  | -0.1  | -0.1
    }

    def "step restarts game in invalid position"(double px, double py, double dt, LevelAction action) {
        given:
        player.getPosition() >> new Vector2D(px, py)

        when:
        levelController.step(action, dt)

        then:
        1 * game.resetState()
        1 * soundPlayer.stopSound()

        where:
        dt   | px    | py   | action
        1.2  | 1.3   | -1   | LevelAction.JUMP
        23   | -1    | -1.1 | LevelAction.NULL
        0    | 0     | -2.3 | LevelAction.JUMP
        0.1  | -42.2 | 0    | LevelAction.NULL
        0    | 0.1   | -0.1 | LevelAction.JUMP

    }

    def "visitBlock does nothing if no collisions"() {
        given:
        def block = Mock(Block)
        block.collision(player) >> false
        block.topCollision(player) >> false

        when:
        levelController.visitBlock(block)

        then:
        0 * playerController.groundPlayer(_)
        0 * game.setState(_)
    }

    def "visitBlock grounds player"(int h) {
        given:
        def block = Mock(Block)
        block.topCollision(player) >> true
        def position = Mock(Vector2D)
        position.getY() >> h
        block.getPosition() >> position

        when:
        levelController.visitBlock(block)

        then:
        1 * playerController.groundPlayer(h + 1)

        where:
        h | _
        2 | _
        3 | _
    }

    def "visitBlock restarts game"() {
        given:
        def block = Mock(Block)
        block.topCollision(player) >> false
        block.collision(player) >> true

        when:
        levelController.visitBlock(block)

        then:
        1 * soundPlayer.stopSound()
        1 * game.resetState()
    }

    def "visitSpike does nothing if no collisions"() {
        given:
        def spike = Mock(Spike)
        spike.collision(player) >> false

        when:
        levelController.visitSpike(spike)

        then:
        0 * playerController.groundPlayer(_)
        0 * game.setState(_)
        0 * soundPlayer.stopSound()
    }

    def "visitSpike restarts game"() {
        given:
        def spike = Mock(Spike)
        spike.collision(player) >> true

        when:
        levelController.visitSpike(spike)

        then:
        1 * soundPlayer.stopSound()
        1 * game.resetState()
    }

    def "visitReversedSpike does nothing if no collisions"() {
        given:
        def spike = Mock(ReversedSpike)
        spike.collision(player) >> false

        when:
        levelController.visitReversedSpike(spike)

        then:
        0 * playerController.groundPlayer(_)
        0 * game.setState(_)
        0 * soundPlayer.stopSound()
    }

    def "visitReversedSpike restarts game"() {
        given:
        def spike = Mock(ReversedSpike)
        spike.collision(player) >> true

        when:
        levelController.visitReversedSpike(spike)

        then:
        1 * soundPlayer.stopSound()
        1 * game.resetState()
    }

    def "visitPlatform does nothing if no collisions"() {
        given:
        def platform = Mock(Platform)
        platform.collision(player) >> false
        platform.topCollision(player) >> false

        when:
        levelController.visitPlatform(platform)

        then:
        0 * playerController.groundPlayer(_)
        0 * game.setState(_)
        0 * soundPlayer.stopSound()
    }

    def "visitPlatform grounds player"(int h) {
        given:
        def platform = Mock(Platform)
        platform.topCollision(player) >> true
        def position = Mock(Vector2D)
        position.getY() >> h
        platform.getPosition() >> position

        when:
        levelController.visitPlatform(platform)

        then:
        1 * playerController.groundPlayer(h + 1)

        where:
        h | _
        2 | _
        3 | _
    }

    def "visitPlatform restarts game"() {
        given:
        def platform = Mock(Platform)
        platform.topCollision(player) >> false
        platform.collision(player) >> true

        when:
        levelController.visitPlatform(platform)

        then:
        1 * soundPlayer.stopSound()
        1 * game.resetState()
    }

    def "visitBoost does nothing if no collisions"() {
        given:
        def boost = Mock(Boost)
        boost.collision(player) >> false

        when:
        levelController.visitBoost(boost)

        then:
        0 * soundPlayer.stopSound()
        0 * playerController.jump(_, _)
    }

    def "visitBoost boosts player"() {
        given:
        def boost = Mock(Boost)
        boost.collision(player) >> true

        when:
        levelController.visitBoost(boost)

        then:
        1 * playerController.jump(5, 0.7)
        1 * player.setGrounded(false)
        1 * player.setOnBoost(true)
    }

    def "visitDoubleJump does nothing if no collisions"() {
        given:
        def doubleJump = Mock(DoubleJump)
        doubleJump.collision(player) >> false

        when:
        levelController.visitDoubleJump(doubleJump)

        then:
        0 * player.setOnDoubleJump(true);
    }
    def "visitDoubleJump allows player to jump"() {
        given:
        def doubleJump = Mock(DoubleJump)
        doubleJump.collision(player) >> true

        when:
        levelController.visitDoubleJump(doubleJump)

        then:
        1 * player.setOnDoubleJump(true);
    }

    def "visitLevelEnd does nothing if no collisions"() {
        given:
        def levelEnd = Mock(LevelEnd)
        levelEnd.collision(player) >> false

        when:
        levelController.visitLevelEnd(levelEnd)

        then:
        0 * soundPlayer.stopSound()
        0 * game.setState(_)
    }

    def "visitLevelEnd ends game"() {
        given:
        def levelEnd = Mock(LevelEnd)
        levelEnd.collision(player) >> true

        when:
        levelController.visitLevelEnd(levelEnd)

        then:
        1 * soundPlayer.stopSound()
        1 * game.setState(_ as MenuState)
    }

    def "updatePointers"(double px, double py, int left, int right) {
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
        def position4 = new Vector2D(3, 0)
        element4.getPosition() >> position4
        def element5 = Mock(Element)
        def position5 = new Vector2D(5, 0)
        element5.getPosition() >> position5

        def model = Mock(LevelModel)
        model.getElements() >> Arrays.asList(element1, element2, element3, element4, element5)

        def player = Mock(Player)
        def playerPosition = new Vector2D(px, py)
        player.getPosition() >> playerPosition
        model.getPlayer() >> player

        def levelController = new LevelController(model, game, playerController, soundPlayer)

        when:
        levelController.updatePointers()

        then:
        levelController.getLeftPointer() == left
        levelController.getRightPointer() == right

        where:
        px  | py    | left | right
        0   | 1     | 0    | 1
        0.5 | 0.25  | 0    | 2
        1   | 150.2 | 0    | 3
        2   | 52.1  | 0    | 4
        4   | 42    | 3    | 5
    }
}

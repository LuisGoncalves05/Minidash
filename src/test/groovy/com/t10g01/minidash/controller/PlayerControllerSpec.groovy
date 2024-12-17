package com.t10g01.minidash.controller


import com.t10g01.minidash.model.Player
import com.t10g01.minidash.model.Vector2D
import com.t10g01.minidash.utils.GameSettings
import spock.lang.Specification

class PlayerControllerSpec extends Specification {

    static GameSettings settings

    def setup() {
        settings = Mock(GameSettings)
        settings.getResolution() >> 10
        settings.getCameraWidth() >> 10
        settings.getCameraHeight() >> 5
        settings.getRotationSpeed() >> -360
    }

    def "update updates position and speed"(xi, yi, vx, vy, g, dt, xf, yf, vxf, vyf) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(xi, yi)
        player.getSpeed() >> new Vector2D(vx, vy)
        player.getG() >> g
        def controller = new PlayerController(player, settings)

        when:
        controller.update(dt)

        then:
        1 * player.setSpeed(new Vector2D(vxf, vyf))
        1 * player.setPosition(new Vector2D(xf, yf))

        where:
        xi  | yi  | vx  | vy  | g    | dt  | xf  | yf  | vxf | vyf
        0   | 1   | 1   | 0   | 10   | 0.1 | 0.1 | 0.9 | 1   |-1
        0   | 1   | 0   | 1   | 20   | 0.1 | 0   | 0.9 | 0   |-1
        2   | 2   |-0.5 | 2   | 4    | 0.5 | 1.75| 2   |-0.5 | 0
    }

    def "update correctly sets previousPosition"(x, y) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(x, y)
        player.getSpeed() >> new Vector2D(0, 0)
        def controller = new PlayerController(player, settings)

        when:
        controller.update(0.1)

        then:
        1 * player.setPreviousPosition(new Vector2D(x, y))

        where:
        x | y
        0 | 1
        0 | 1
        2 | 2
    }

    def "update unsets isGrounded"(xi, yi, vx, vy, g, dt, xf, yf, vxf, vyf) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(xi, yi)
        player.getSpeed() >> new Vector2D(vx, vy)
        player.getG() >> g
        def controller = new PlayerController(player, settings)

        when:
        controller.update(dt)

        then:
        1 * player.setGrounded(false)

        where:
        xi  | yi  | vx  | vy  | g    | dt  | xf  | yf  | vxf | vyf
        0   | 1   | 1   | 0   | 10   | 0.1 | 0.1 | 0.9 | 1   | -1
        0   | 1   | 0   | 1   | 20   | 0.1 | 0   | 0.8 | 0   | -2
        2   | 2   |-0.5 | 2   | 4    | 0.5 | 1.75| 2   |-0.5 | 0
    }

    def "jump doesn't jump if not allowed"(vxi, vyi, height, time) {
        given:
        def player = Mock(Player)
        player.getSpeed() >> new Vector2D(vxi, vyi)
        player.canJump() >> false
        def controller = new PlayerController(player, settings)

        when:
        controller.jump(height, time)

        then:
        0 * player.setSpeed(_)
        0 * player.setG();

        where:
        vxi | vyi | height | time
        0   | 0   | 10     | 2
        2   | 5   | 8      | 4
        3   | 0   | 6      | 4
    }

    def "jump updates speed"(vxi, vyi, height, time, vxf, vyf) {
        given:
        def player = Mock(Player)
        player.getSpeed() >> new Vector2D(vxi, vyi)
        player.canJump() >> true
        def controller = new PlayerController(player, settings)

        when:
        controller.jump(height, time)

        then:
        1 * player.setSpeed(new Vector2D(vxf, vyf))

        where:
        vxi | vyi | height | time | vxf | vyf
        0   | 0   | 10     | 2    | 0   | 20
        2   | 5   | 8      | 4    | 2   | 8
        3   | 0   | 6      | 4    | 3   | 6
    }

    def "jump updates g"(vxi, vyi, height, time, vxf, vyf, g) {
        given:
        def player = Mock(Player)
        player.getSpeed() >> new Vector2D(vxi, vyi)
        player.canJump() >> true
        def controller = new PlayerController(player, settings)

        when:
        controller.jump(height, time)

        then:
        1 * player.setG(g)

        where:
        vxi | vyi | height | time | vxf | vyf | g
        0   | 0   | 10     | 2    | 0   | 20  | 20
        2   | 5   | 8      | 4    | 2   | 20  | 4
        3   | 0   | 6      | 4    | 3   | 15  | 3
    }

    def "setGrounded sets player height"(h, xi, yi, xf, yf) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(xi, yi)
        def controller = new PlayerController(player, settings)

        when:
        controller.groundPlayer(h)

        then:
        1 * player.setPosition(new Vector2D(xf, yf))

        where:
        h | xi | yi | xf | yf
        1 | 0  | 0  | 0  | 1
        2 | 3  | 1  | 3  | 2
        3 | 5  | 1  | 5  | 3
    }

    def "setGrounded sets grounded"(h) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(xi, yi)
        def controller = new PlayerController(player, settings)

        when:
        controller.groundPlayer(h)

        then:
        1 * player.setGrounded(true)

        where:
        h | xi | yi
        1 | 0  | 0
        2 | 3  | 1
        3 | 5  | 1
    }

    def "setGrounded resets g"(h) {
        given:
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(xi, yi)
        def controller = new PlayerController(player, settings)

        when:
        controller.groundPlayer(h)

        then:
        1 * player.setG(Player.defaultG)

        where:
        h | xi | yi
        1 | 0  | 0
        2 | 3  | 1
        3 | 5  | 1
    }

    def "jump updates rotation"(ground, rot, dt, rotf) {
        given:
        def player = Mock(Player)
        player.getGrounded() >> ground
        player.getRotation() >> rot

        player.getSpeed() >> new Vector2D(0, 0)
        player.getPosition() >> new Vector2D(0, 0)

        when:
        def controller = new PlayerController(player, settings)
        controller.update(dt)

        then:
        1 * player.setRotation(rotf)

        where:
        ground  | rot | dt  | rotf
        true    | 0   | 0.1 | 0
        true    | 72  | 0.1 | 0
        false   | 0   | 0.1 | -36
        false   | 0   | 0.5 | -180
        false   | 30  | 0.1 | -6



    }
}
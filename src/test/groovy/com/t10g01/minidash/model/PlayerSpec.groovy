package com.t10g01.minidash.model

import spock.lang.Specification

class PlayerSpec extends Specification {

    def "setGrounded sets speed to 0 when grounded"() {
        given:
        def speed = Mock(Vector2D)
        speed.getY() >> 5;
        def player = new Player(0,0)
        player.setSpeed(speed)

        when:
        player.setGrounded(true)

        then:
        1 * speed.setY(0)
    }

    def "setGrounded doesn't set speed to 0 when not grounded"() {
        given:
        def speed = Mock(Vector2D)
        speed.getY() >> 5;
        def player = new Player(0, 0)
        player.setSpeed(speed)

        when:
        player.setGrounded(false)

        then:
        0 * speed.setY(0)
    }
}

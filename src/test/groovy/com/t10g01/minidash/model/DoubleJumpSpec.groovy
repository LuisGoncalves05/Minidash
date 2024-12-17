package com.t10g01.minidash.model

import spock.lang.Shared
import spock.lang.Specification

class DoubleJumpSpec extends Specification {

    @Shared
    BoxCollider boundingBox, collider1, collider2
    @Shared
    DoubleJump doubleJump
    @Shared
    Player player

    def setup() {
        boundingBox = Mock(BoxCollider)
        collider1 = Mock(BoxCollider)
        collider2 = Mock(BoxCollider)
        doubleJump = new DoubleJump(boundingBox, Arrays.asList(collider1, collider2))
        player = Mock(Player)
    }

    def 'doubleJump collision checks boundingBox first'() {
        given:
        boundingBox.collision(player) >> false
        collider1.collision(player) >> true
        collider2.collision(player) >> true

        when:
        doubleJump.collision(player)

        then:
        1 * boundingBox.collision(player)
        0 * collider1.collision(player)
        0 * collider2.collision(player)
    }

    def 'collision with doubleJump colliders'() {
        given:
        boundingBox.collision(player) >> true
        collider1.collision(player) >> a
        collider2.collision(player) >> b

        expect:
        doubleJump.collision(player) == result

        where:
        a     | b     | result
        false | false | false
        false | true  | true
        true  | false | true
        true  | true  | true
    }
}

package com.t10g01.minidash.model

import spock.lang.Shared
import spock.lang.Specification

class SpikeSpec extends  Specification {
    @Shared
    BoxCollider boundingBox, collider1, collider2, collider3
    @Shared
    Spike spike
    @Shared
    Player player

    def setup() {
        boundingBox = Mock(BoxCollider)
        collider1 = Mock(BoxCollider)
        collider2 = Mock(BoxCollider)
        collider3 = Mock(BoxCollider)
        spike = new Spike(boundingBox, Arrays.asList(collider1, collider2, collider3))
        player = Mock(Player)
    }

    def "collision checks boundingBox first"() {
        given:
        boundingBox.collision(player) >> false
        collider1.collision(player) >> true
        collider2.collision(player) >> true
        collider3.collision(player) >> true

        expect:
        !spike.collision(player)
    }

    def "collision detects collision with colliders"() {
        given:
        boundingBox.collision(player) >> true
        collider1.collision(player) >> a
        collider2.collision(player) >> b
        collider3.collision(player) >> c

        expect:
        spike.collision(player) == result

        where:
        a     | b     | c     | result
        false | false | false | false
        false | false | true  | true
        false | true  | false | true
        false | true  | true  | true
        true  | false | false | true
        true  | false | true  | true
        true  | true  | false | true
        true  | true  | true  | true
    }
}

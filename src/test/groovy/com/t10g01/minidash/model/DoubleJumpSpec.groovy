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

    def 'doubleJump generates correct bounding box'(x, y, xl, yl, xu, yu) {
        when:
        def doubleJump = new DoubleJump(x, y);
        def boundingBox = doubleJump.getBoundingBox()
        def d = 1e-3

        then:
        Math.abs(boundingBox.getLowerLeft().getX() - xl) < d
        Math.abs(boundingBox.getLowerLeft().getY() - yl) < d
        Math.abs(boundingBox.getUpperRight().getX() - xu) < d
        Math.abs(boundingBox.getUpperRight().getY() - yu) < d

        where:
        x | y | xl            | yl            | xu            | yu
        0 | 0 | 0.3 as Double | 0.3 as Double | 0.7 as Double | 0.7 as Double
        1 | 1 | 1.3 as Double | 1.3 as Double | 1.7 as Double | 1.7 as Double
    }

    def 'doubleJump generates all colliders'(x, y, xl, yl) {
        when:
        def doubleJump = new DoubleJump(x, y);
        def colliders = doubleJump.getColliders()
        def d = 1e-3

        then:
        colliders.size() == 2

        Math.abs(colliders.get(0).getLowerLeft().getX() - (xl + 0.35d)) < d
        Math.abs(colliders.get(0).getLowerLeft().getY() - (yl + 0.3d)) < d
        Math.abs(colliders.get(1).getLowerLeft().getX() - (xl + 0.3d)) < d
        Math.abs(colliders.get(1).getLowerLeft().getY() - (yl + 0.35d)) < d

        Math.abs(colliders.get(0).getUpperRight().getX() - (xl + 0.65d)) < d
        Math.abs(colliders.get(0).getUpperRight().getY() - (yl + 0.7d)) < d
        Math.abs(colliders.get(1).getUpperRight().getX() - (xl + 0.7d)) < d
        Math.abs(colliders.get(1).getUpperRight().getY() - (yl + 0.65d)) < d

        where:
        x | y | xl | yl
        0 | 0 | 0  | 0
        1 | 1 | 1  | 1
        1 | 3 | 1  | 3
    }

    def 'doublejump collision checks boundingBox first'() {
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

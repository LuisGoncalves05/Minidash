package com.t10g01.minidash.model

import spock.lang.Shared
import spock.lang.Specification

class BoostSpec extends Specification {
    @Shared
    BoxCollider boundingBox, collider1, collider2
    @Shared
    Boost boost
    @Shared
    Player player

    def setup() {
        boundingBox = Mock(BoxCollider)
        collider1 = Mock(BoxCollider)
        collider2 = Mock(BoxCollider)
        boost = new Boost(boundingBox, Arrays.asList(collider1, collider2))
        player = Mock(Player)
    }

    def 'boost generates correct bounding box'(x, y, xl, yl, xu, yu) {
        when:
        def boost = new Boost(x, y);
        def boundingBox = boost.getBoundingBox()
        def d = 1e-3

        then:
        Math.abs(boundingBox.getLowerLeft().getX() - xl) < d
        Math.abs(boundingBox.getLowerLeft().getY() - yl) < d
        Math.abs(boundingBox.getUpperRight().getX() - xu) < d
        Math.abs(boundingBox.getUpperRight().getY() - yu) < d

        where:
        x | y | xl            | yl | xu            | yu
        0 | 0 | 0.1 as Double | 0  | 0.9 as Double | 0.2 as Double
        1 | 1 | 1.1 as Double | 1  | 1.9 as Double | 1.2 as Double
    }

    def 'boost generates all colliders'(x, y, xl, yl) {
        when:
        def boost = new Boost(x, y);
        def colliders = boost.getColliders()
        def d = 1e-3

        then:
        colliders.size() == 2

        Math.abs(colliders.get(0).getLowerLeft().getX() - (xl + 0.1d)) < d
        Math.abs(colliders.get(0).getLowerLeft().getY() - (yl)) < d
        Math.abs(colliders.get(1).getLowerLeft().getX() - (xl + 0.2d)) < d
        Math.abs(colliders.get(1).getLowerLeft().getY() - (yl + 0.1d)) < d

        Math.abs(colliders.get(0).getUpperRight().getX() - (xl + 0.9d)) < d
        Math.abs(colliders.get(0).getUpperRight().getY() - (yl + 0.1d)) < d
        Math.abs(colliders.get(1).getUpperRight().getX() - (xl + 0.8d)) < d
        Math.abs(colliders.get(1).getUpperRight().getY() - (yl + 0.2d)) < d

        where:
        x | y | xl | yl
        0 | 0 | 0  | 0
        1 | 1 | 1  | 1
        1 | 3 | 1  | 3
    }

    def 'boost collision checks boundingBox first'() {
        given:
        boundingBox.collision(player) >> false
        collider1.collision(player) >> true
        collider2.collision(player) >> true

        when:
        boost.collision(player)

        then:
        1 * boundingBox.collision(player)
        0 * collider1.collision(player)
        0 * collider2.collision(player)
    }

    def 'collision with boost colliders'(a, b, result) {
        given:
        boundingBox.collision(player) >> true
        collider1.collision(player) >> a
        collider2.collision(player) >> b

        expect:
        boost.collision(player) == result

        where:
        a     | b     | result
        false | false | false
        false | true  | true
        true  | false | true
        true  | true  | true
    }
}

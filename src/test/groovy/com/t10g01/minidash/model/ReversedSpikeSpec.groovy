package com.t10g01.minidash.model

import spock.lang.Shared
import spock.lang.Specification

class ReversedSpikeSpec extends Specification {
    @Shared
    BoxCollider boundingBox, collider1, collider2, collider3
    @Shared
    ReversedSpike spike
    @Shared
    Player player

    def setup() {
        boundingBox = Mock(BoxCollider)
        collider1 = Mock(BoxCollider)
        collider2 = Mock(BoxCollider)
        collider3 = Mock(BoxCollider)
        spike = new ReversedSpike(boundingBox, Arrays.asList(collider1, collider2, collider3))
        player = Mock(Player)
    }

    def 'reversedSpike generates correct bounding box'(x, y, xl, yl, xu, yu) {
        when:
        def spike = new ReversedSpike(x, y);
        def boundingBox = spike.getBoundingBox()

        then:
        boundingBox.getLowerLeft().getX() == xl
        boundingBox.getLowerLeft().getY() == yl
        boundingBox.getUpperRight().getX() == xu
        boundingBox.getUpperRight().getY() == yu

        where:
        x | y | xl | yl            | xu | yu
        0 | 0 | 0  | 0.5 as Double | 1  | 1
        1 | 1 | 1  | 1.5 as Double | 2  | 2
    }

    def 'reversedSpike generates all colliders'(x, y, xl, yl) {
        when:
        def spike = new ReversedSpike(x, y);
        def colliders = spike.getColliders()
        def d = 1e-3

        then:
        colliders.size() == 4

        Math.abs(colliders.get(0).getLowerLeft().getX() - (xl + 0.1d)) < d
        Math.abs(colliders.get(0).getLowerLeft().getY() - (yl + 0.9d)) < d
        Math.abs(colliders.get(1).getLowerLeft().getX() - (xl + 0.2d)) < d
        Math.abs(colliders.get(1).getLowerLeft().getY() - (yl + 0.8d)) < d
        Math.abs(colliders.get(2).getLowerLeft().getX() - (xl + 0.3d)) < d
        Math.abs(colliders.get(2).getLowerLeft().getY() - (yl + 0.7d)) < d
        Math.abs(colliders.get(3).getLowerLeft().getX() - (xl + 0.4d)) < d
        Math.abs(colliders.get(3).getLowerLeft().getY() - (yl + 0.6d)) < d

        Math.abs(colliders.get(0).getUpperRight().getX() - (xl + 0.9d)) < d
        Math.abs(colliders.get(0).getUpperRight().getY() - (yl + 1.0d)) < d
        Math.abs(colliders.get(1).getUpperRight().getX() - (xl + 0.8d)) < d
        Math.abs(colliders.get(1).getUpperRight().getY() - (yl + 0.9d)) < d
        Math.abs(colliders.get(2).getUpperRight().getX() - (xl + 0.7d)) < d
        Math.abs(colliders.get(2).getUpperRight().getY() - (yl + 0.8d)) < d
        Math.abs(colliders.get(3).getUpperRight().getX() - (xl + 0.6d)) < d
        Math.abs(colliders.get(3).getUpperRight().getY() - (yl + 0.7d)) < d

        where:
        x | y | xl | yl
        0 | 0 | 0  | 0
        1 | 1 | 1  | 1
        1 | 3 | 1  | 3
    }

    def 'collision checks bounding box first'() {
        given:
        boundingBox.collision(player) >> false
        collider1.collision(player) >> true
        collider2.collision(player) >> true
        collider3.collision(player) >> true

        expect:
        !spike.collision(player)
    }

    def 'collision with reversedSpike colliders'(a, b, c, result) {
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

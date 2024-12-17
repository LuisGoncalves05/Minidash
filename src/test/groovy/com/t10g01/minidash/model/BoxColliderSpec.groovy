package com.t10g01.minidash.model

import spock.lang.Shared
import spock.lang.Specification

class BoxColliderSpec extends Specification {
    static dx = 0.1d
    static dy = 0.1d

    def "collides upperleft"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(playerX, playerY)

        expect:
        boxCollider.collision(player) == collides

        where:
        playerX   | playerY   | collides
        1.0d + dx | 3.0d      | true
        1.0d + dx | 3.0d + dy | false
        1.0d + dx | 3.0d - dy | true
        1.0d - dx | 3.0d      | false
        1.0d - dx | 3.0d + dy | false
        1.0d - dx | 3.0d - dy | false
        1.0d      | 3.0d      | true
        1.0d      | 3.0d + dy | false
        1.0d      | 3.0d - dy | true
    }

    def "collides upperright"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(playerX, playerY)

        expect:
        boxCollider.collision(player) == collides

        where:
        playerX   | playerY   | collides
        3.0d + dx | 3.0d      | false
        3.0d + dx | 3.0d + dy | false
        3.0d + dx | 3.0d - dy | false
        3.0d - dx | 3.0d      | true
        3.0d - dx | 3.0d + dy | false
        3.0d - dx | 3.0d - dy | true
        3.0d      | 3.0d      | true
        3.0d      | 3.0d + dy | false
        3.0d      | 3.0d - dy | true
    }

    def "collides lowerleft"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(playerX, playerY)

        expect:
        boxCollider.collision(player) == collides

        where:
        playerX   | playerY   | collides
        1.0d + dx | 1.0d      | true
        1.0d + dx | 1.0d + dy | true
        1.0d + dx | 1.0d - dy | false
        1.0d - dx | 1.0d      | false
        1.0d - dx | 1.0d + dy | false
        1.0d - dx | 1.0d - dy | false
        1.0d      | 1.0d      | true
        1.0d      | 1.0d + dy | true
        1.0d      | 1.0d - dy | false
    }

    def "collides lowerright"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(playerX, playerY)

        expect:
        boxCollider.collision(player) == collides

        where:
        playerX   | playerY   | collides
        3.0d + dx | 1.0d      | false
        3.0d + dx | 1.0d + dy | false
        3.0d + dx | 1.0d - dy | false
        3.0d - dx | 1.0d      | true
        3.0d - dx | 1.0d + dy | true
        3.0d - dx | 1.0d - dy | false
        3.0d      | 1.0d      | true
        3.0d      | 1.0d + dy | true
        3.0d      | 1.0d - dy | false
    }

    def "topCollision on small collider"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 0.5, 0.5)
        def player = Mock(Player)
        player.getPosition() >> new Vector2D(playerX, playerY)

        expect:
        boxCollider.collision(player) == collides

        where:
        playerX   | playerY   | collides
        1.0d + dx | 2.5d      | true
        1.0d + dx | 2.5d + dy | false
        1.0d + dx | 2.5d - dy | true
        1.0d - dx | 2.5d      | false
        1.0d - dx | 2.5d + dy | false
        1.0d - dx | 2.5d - dy | false
        1.0d      | 2.5d      | true
        1.0d      | 2.5d + dy | false
        1.0d      | 2.5d - dy | true
        2.5d + dx | 2.5d      | false
        2.5d + dx | 2.5d + dy | false
        2.5d + dx | 2.5d - dy | false
        2.5d - dx | 2.5d      | true
        2.5d - dx | 2.5d + dy | false
        2.5d - dx | 2.5d - dy | true
        2.5d      | 2.5d      | true
        2.5d      | 2.5d + dy | false
        2.5d      | 2.5d - dy | true
        1.0d + dx | 1.0d      | true
        1.0d + dx | 1.0d + dy | true
        1.0d + dx | 1.0d - dy | false
        1.0d - dx | 1.0d      | false
        1.0d - dx | 1.0d + dy | false
        1.0d - dx | 1.0d - dy | false
        1.0d      | 1.0d      | true
        1.0d      | 1.0d + dy | true
        1.0d      | 1.0d - dy | false
        2.5d + dx | 1.0d      | false
        2.5d + dx | 1.0d + dy | false
        2.5d + dx | 1.0d - dy | false
        2.5d - dx | 1.0d      | true
        2.5d - dx | 1.0d + dy | true
        2.5d - dx | 1.0d - dy | false
        2.5d      | 1.0d      | true
        2.5d      | 1.0d + dy | true
        2.5d      | 1.0d - dy | false
    }

    def "topCollision doesn't collide from top"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPreviousPosition() >> new Vector2D(previousPlayerX, previousPlayerY);
        player.getPosition() >> new Vector2D(currentPlayerX, currentPlayerY)

        expect:
        !boxCollider.topCollision(player)

        where:
        previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY
        0.0d            |  1.0d           | 0.0d           |  0.2d
        0.5d            |  1.5d           | 0.99d          |  0.99d
        1.0d            |  1.9d           | 2.2d           |  0.99d
        1.5d            |  0.5d           | 0.99d          |  2.2d
        1.0d            |  0.0d           | 3.1d           |  1.9d
        0.42d           |  0.01d          | 1.9d           |  3.2d
    }

    def "topCollision doesn't collide from bottom"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPreviousPosition() >> new Vector2D(previousPlayerX, previousPlayerY);
        player.getPosition() >> new Vector2D(currentPlayerX, currentPlayerY)

        expect:
        !boxCollider.topCollision(player)

        where:
        previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY
        0.0d            |  1.0d           | 1.0d           |  1.0d
        0.5d            |  1.5d           | 1.4d           |  1.2d
        1.0d            |  1.9d           | 1.8d           |  2.2d
        1.5d            |  0.5d           | 2.2d           |  1.0d
        1.0d            |  0.0d           | 2.6d           |  1.2d
        0.42d           |  0.01d          | 3.0d           |  2.2d
    }

    def "topCollision collides from top"() {
        given:
        def boxCollider = new BoxCollider(2, 2, 1, 1)
        def player = Mock(Player)
        player.getPreviousPosition() >> new Vector2D(previousPlayerX, previousPlayerY);
        player.getPosition() >> new Vector2D(currentPlayerX, currentPlayerY)

        expect:
        boxCollider.topCollision(player)

        where:
        previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY
        0.0d            |  5.0d           | 1.0d           |  1.0d
        0.5d            |  4.5d           | 1.4d           |  1.2d
        1.0d            |  4.0d           | 1.8d           |  2.2d
        1.5d            |  3.5d           | 2.2d           |  1.0d
        1.0d            |  3.0d           | 2.6d           |  1.2d
        0.42d           |  42.0d          | 3.0d           |  2.2d
    }
}

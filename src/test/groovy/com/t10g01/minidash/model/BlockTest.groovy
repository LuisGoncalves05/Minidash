package com.t10g01.minidash.model

import com.t10g01.minidash.shared.Position
import spock.lang.Shared
import spock.lang.Specification

class BlockTest extends Specification {

    @Shared
    def block = new Block(2.0d, 2.0d)

    @Shared
    def dx = 0.1d;

    @Shared
    def dy = 0.1d;

    def "collides upperleft"() {
        expect:
            block.collides(new Position(playerX, playerY)) == collides
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
        expect:
            block.collides(new Position(playerX, playerY)) == collides
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
        expect:
            block.collides(new Position(playerX, playerY)) == collides
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
        expect:
            block.collides(new Position(playerX, playerY)) == collides
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

    def "collidesWithTop doesn't collide"() {
        expect:
            block.topCollision(new Position(currentPlayerX, currentPlayerY), new Position(previousPlayerX, previousPlayerY)) == collides
        where:
            previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY | collides
            0.0d            |  3.0d           | 0.5d           |  1.0d          | false
            0.1d            |  2.0d           | 0.6d           |  1.2d          | false
            0.2d            |  1.0d           | 0.7d           |  3.2d          | false
    }

   def "collidesWithTop collides but from bottom"() {
        expect:
            block.topCollision(new Position(currentPlayerX, currentPlayerY), new Position(previousPlayerX, previousPlayerY)) == collides
        where:
            previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY | collides
            1.5d            |  1.5d           | 1.1d           |  1.0d          | false
            1.0d            |  1.0d           | 1.5d           |  1.2d          | false
            1.6d            |  0.5d           | 1.9d           |  2.2d          | false
   }

       def "collidesWithTop collides from top"() {
        expect:
            block.topCollision(new Position(currentPlayerX, currentPlayerY), new Position(previousPlayerX, previousPlayerY)) == collides
        where:
            previousPlayerX | previousPlayerY | currentPlayerX | currentPlayerY | collides
            0.0d            |  4.0d           | 1.5d           |  1.0d          | true
            0.1d            |  3.0d           | 1.6d           |  1.2d          | true
            0.2d            |  3.0d           | 1.7d           |  2.2d          | true
   }
}
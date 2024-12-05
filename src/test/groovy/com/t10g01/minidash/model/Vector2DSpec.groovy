package com.t10g01.minidash.model

import spock.lang.Specification

class Vector2DSpec extends Specification {

    def 'vector rotation around (0, 0)'(xi, yi, angle, xf, yf) {
        given:
        def vector = new Vector2D(xi, yi)

        when:
        vector.rotate(angle)

        then:
        Math.abs(vector.getX() - xf) < 1E-5;
        Math.abs(vector.getY() - yf) < 1E-5;

        where:
        xi | yi | angle | xf                | yf
        1  | 0  | 90    | 0                 | 1
        1  | 0  | -90   | 0                 | -1
        1  | 0  | 270   | 0                 | -1
        1  | 0  | 180   | -1                | 0
        1  | 0  | 360   | 1                 | 0
        1  | 0  | 45    | Math.sqrt(2) / 2  | Math.sqrt(2) / 2
        1  | 0  | 30    | Math.sqrt(3) / 2  | 1 / 2
        1  | 0  | 60    | 1 / 2             | Math.sqrt(3) / 2

    }
}

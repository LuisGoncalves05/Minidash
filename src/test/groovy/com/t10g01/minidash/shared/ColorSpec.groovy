package com.t10g01.minidash.shared

import spock.lang.Specification

class ColorSpec extends Specification {

    def "get color pink"() {
        given:
        String hex = "#FFC0CB"

        when:
        def color = new Color(hex)
        int red = color.getRed()
        int green = color.getGreen()
        int blue = color.getBlue()

        then:
        red == 255
        green == 192
        blue == 203
    }

    def "set color to blue"() {
        given:
        String hex = "#FFFFFF"

        when:
        def color = new Color(hex)
        color.setRed(0)
        color.setGreen(0)
        color.setBlue(255)
        int red = color.getRed()
        int green = color.getGreen()
        int blue = color.getBlue()

        then:
        red == 0
        green == 0
        blue == 255
    }

    def "invalid color"() {
        given:
        String hex = "FFC0CB"

        when:
        def color = new Color(hex)

        then:
        thrown(StringIndexOutOfBoundsException)

    }

}

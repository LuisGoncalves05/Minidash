package com.t10g01.minidash.view

import com.t10g01.minidash.io.LanternaIO
import com.t10g01.minidash.model.MenuOption
import com.t10g01.minidash.utils.Color
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.model.MenuModel
import spock.lang.Shared
import spock.lang.Specification


class MenuViewSpec extends Specification {
    @Shared
    LanternaIO lanternaIO
    @Shared
    MenuModel menuModel
    @Shared
    MenuView menuView
    @Shared
    MenuOption menuOption1
    @Shared
    MenuOption menuOption2
    @Shared
    GameSettings settings

    def setup() {
        lanternaIO = Mock(LanternaIO)
        lanternaIO.getScreenHeight() >> 90
        lanternaIO.getScreenWidth() >> 6

        menuModel = Mock(MenuModel)
        menuModel.getSelected() >> 1;

        menuOption1 = Mock(MenuOption)
        menuOption2 = Mock(MenuOption)

        def options = Arrays.asList(menuOption1, menuOption2)
        menuModel.getOptions() >> options

        settings = Mock(GameSettings)

        def colorNS = Mock(Color)
        colorNS.red >> 255
        colorNS.green >> 0
        colorNS.blue >> 0
        settings.getMenuOptionColor() >> colorNS

        def colorS = Mock(Color)
        colorS.red >> 2
        colorS.green >> 3
        colorS.blue >> 4
        settings.getSelectedOptionColor() >> colorS

        menuView = new MenuView(menuModel, lanternaIO, settings)
    }

    def "draw sets rendered options"() {
        when:
        menuView.draw()

        then:
        1 * menuOption1.accept(menuView)
        1 * menuOption2.accept(menuView)
        1 * lanternaIO.clear()
        1 * lanternaIO.refresh()
    }

    def "drawOption draws correct pixels and updates renderedOptions"() {
        given:
        def colorNotSelected = settings.getMenuOptionColor()
        def colorSelected = settings.getSelectedOptionColor()

        expect:
        menuView.renderedOptions == 0

        when:
        menuView.drawOption("test.png")

        then: "all pixels drawn (test has width 4 but only 3 visible pixels)"
        1 * lanternaIO.drawPixel(1, 79, colorNotSelected)
        1 * lanternaIO.drawPixel(2, 79, colorNotSelected)
        1 * lanternaIO.drawPixel(3, 79, colorNotSelected)
        1 * lanternaIO.drawPixel(1, 78, colorNotSelected)
        1 * lanternaIO.drawPixel(2, 78, colorNotSelected)
        1 * lanternaIO.drawPixel(1, 77, colorNotSelected)
        0 * lanternaIO.drawPixel(_, _, colorNotSelected)

        menuView.renderedOptions == 1

        when:
        menuView.drawOption("test.png")

        then:
        1 * lanternaIO.drawPixel(1, 39, colorSelected)
        1 * lanternaIO.drawPixel(2, 39, colorSelected)
        1 * lanternaIO.drawPixel(3, 39, colorSelected)
        1 * lanternaIO.drawPixel(1, 38, colorSelected)
        1 * lanternaIO.drawPixel(2, 38, colorSelected)
        1 * lanternaIO.drawPixel(1, 37, colorSelected)
        0 * lanternaIO.drawPixel(_, _, colorSelected)

        menuView.renderedOptions == 2
    }
}

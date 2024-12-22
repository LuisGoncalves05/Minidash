package com.t10g01.minidash.view

import com.t10g01.minidash.ioadapter.IOAdapter
import com.t10g01.minidash.model.MenuOption
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.model.MenuModel
import spock.lang.Shared
import spock.lang.Specification


class MenuViewSpec extends Specification {
    @Shared
    IOAdapter ioAdapter
    @Shared
    MenuModel menuModel
    @Shared
    MenuView menuView

    def setup() {
        ioAdapter = Mock(IOAdapter)
        ioAdapter.getScreenHeight() >> 30
        ioAdapter.getScreenWidth() >> 4
        menuModel = Mock(MenuModel)
        def options = Mock(List<MenuOption>)
        options.size() >> 1
        menuModel.getOptions() >> options
        menuView = new MenuView(menuModel, ioAdapter, Mock(GameSettings))
    }

    def "drawOption draws correct pixels"() {
        when:
        menuView.drawOption("test.png")

        then:
        1 * ioAdapter.drawPixel(0, 29, _)
        1 * ioAdapter.drawPixel(1, 29, _)
        1 * ioAdapter.drawPixel(2, 29, _)
        1 * ioAdapter.drawPixel(0, 28, _)
        1 * ioAdapter.drawPixel(1, 28, _)
        1 * ioAdapter.drawPixel(0, 27, _)
        0 * ioAdapter.drawPixel(_, _, _)
    }
}

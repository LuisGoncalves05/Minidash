package com.t10g01.minidash.controller

import com.t10g01.minidash.Game
import com.t10g01.minidash.model.ExitButton
import com.t10g01.minidash.model.LevelButton
import com.t10g01.minidash.model.LevelCompleteButton
import com.t10g01.minidash.model.LevelsButton
import com.t10g01.minidash.model.MenuModel
import com.t10g01.minidash.model.MenuOption
import com.t10g01.minidash.state.LevelMenuState
import com.t10g01.minidash.state.LevelState
import com.t10g01.minidash.state.MainMenuState
import com.t10g01.minidash.utils.GameSettings
import com.t10g01.minidash.utils.LevelAction
import com.t10g01.minidash.utils.MenuAction
import spock.lang.Shared
import spock.lang.Specification

import java.awt.Button

class MenuControllerSpec extends Specification {
    @Shared
    MenuModel model
    @Shared
    Game game
    @Shared
    MenuController controller

    def setup() {
        model = Mock(MenuModel)
        game = Mock(Game)
        controller = new MenuController(game, model)
        game.getGameSettings() >> Mock(GameSettings)
    }

    def "step ignores null action"() {
        given:
        def action = MenuAction.NULL

        when:
        controller.step(action, 0)

        then:
        0 * _
    }

    def "step updates elapsedTime"() {
        given:
        def action = MenuAction.NULL
        controller.setElapsedTime(ti)

        when:
        controller.step(action, dt)

        then:
        controller.getElapsedTime() == tf

        where:
        ti | dt | tf
        1  | 2  | 3
        0  | 4  | 4
    }

    def "step refuses to take action when waiting for cooldown"() {
        given:
        def action = MenuAction.UP
        controller.setElapsedTime(t)

        when:
        controller.step(action, 0)

        then:
        0 * _

        where:
        t   | _
        0.1 | _
        0.2 | _
    }

    def "step exits game"() {
        given:
        def action = MenuAction.EXIT
        controller.setElapsedTime(0.25)

        def option1 = Mock(MenuOption)
        def option2 = Mock(MenuOption)
        model.getOptions() >> Arrays.asList(option1, option2)

        when:
        controller.step(action, 0)

        then:
        1 * game.setState(null)
    }

    def "step selects correct action"() {
        given:
        def option1 = Mock(MenuOption)
        def option2 = Mock(MenuOption)
        model.getOptions() >> Arrays.asList(option1, option2)
        model.getSelected() >> 0 >> 1
        def action = MenuAction.SELECT
        controller.setElapsedTime(0.25)

        when:
        controller.step(action, 0)

        then:
        1 * option1.accept(controller)
        0 * option2.accept(controller)

        when:
        controller.step(action, 0.25)

        then:
        0 * option1.accept(controller)
        1 * option2.accept(controller)
    }

    def "step correctly changes selected action"() {
        given:
        def options = Mock(ArrayList<MenuOption>)
        model.getOptions() >> options
        options.size() >> l
        model.getSelected() >> si
        controller.setElapsedTime(0.25)

        when:
        controller.step(action, 0)

        then:
        model.setSelected(sf)

        where:
        l | si | action          | sf
        2 | 0  | MenuAction.DOWN | 1
        2 | 1  | MenuAction.DOWN | 0
        2 | 1  | MenuAction.UP   | 0
        2 | 0  | MenuAction.UP   | 1
        3 | 2  | MenuAction.DOWN | 0
        2 | 0  | MenuAction.UP   | 2
    }

    def "visitLevelsButton sets state to LevelMenuState"() {
        when:
        controller.visitLevelsButton(Mock(LevelsButton));
        then:
        1 * game.setState(_ as LevelMenuState)
    }

    def "visitExitButton sets state to null"() {
        when:
        controller.visitExitButton(Mock(ExitButton));
        then:
        1 * game.setState(null)
    }

    def "visitExitButton sets state to null"() {
        when:
        controller.visitExitButton(Mock(ExitButton));
        then:
        1 * game.setState(null)
    }

    def "acceptLevelComplete sets state to MainMenuState"() {
        when:
        controller.acceptLevelComplete(Mock(LevelCompleteButton));
        then:
        1 * game.setState(_ as MainMenuState)
    }

}

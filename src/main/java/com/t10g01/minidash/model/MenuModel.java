package com.t10g01.minidash.model;

import java.util.List;

public class MenuModel {
    private final List<MenuOption> options;
    private int selected = 0;

    public MenuModel(List<MenuOption> options) {
        this.options = options;
    }

    public List<MenuOption> getOptions() {
        return options;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}

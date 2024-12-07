package com.t10g01.minidash.model;

public interface MenuOption {
    void accept(MenuOptionVisitor visitor);
}

package com.t10g01.minidash.model;

public class ExitButton implements MenuOption {
    @Override
    public void accept(MenuOptionVisitor visitor) {
        visitor.visitExitButton(this);
    }
}

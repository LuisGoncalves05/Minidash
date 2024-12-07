package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExitButton implements MenuOption {
    @Override
    public void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException {
        visitor.visitExitButton(this);
    }
}

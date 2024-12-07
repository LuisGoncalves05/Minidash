package com.t10g01.minidash.model;

import java.io.IOException;
import java.net.URISyntaxException;

public interface MenuOption {
    void accept(MenuOptionVisitor visitor) throws URISyntaxException, IOException;
}

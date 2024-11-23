package com.t10g01.minidash.view;

import com.t10g01.minidash.model.Block;

public interface CollidableVisitor {

    void visitBlock(Block block);

}

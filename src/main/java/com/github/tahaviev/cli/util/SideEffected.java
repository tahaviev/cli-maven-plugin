package com.github.tahaviev.cli.util;

import java.util.function.Supplier;

public final class SideEffected<T> extends Delegated<T> {

    public SideEffected(
        final Runnable effect,
        final Supplier<? extends T> origin
    ) {
        super(
            () -> {
                effect.run();
                return origin.get();
            }
        );
    }

}

package com.github.tahaviev.cli.util;

import java.util.function.Supplier;

/**
 * Object factory with side effect.
 *
 * @param <T> result type
 */
public final class SideEffected<T> extends Delegated<T> {

    /**
     * Constructor.
     *
     * @param effect effect
     * @param origin origin
     */
    public SideEffected(
        final Runnable effect, final Supplier<? extends T> origin
    ) {
        super(
            () -> {
                effect.run();
                return origin.get();
            }
        );
    }

}

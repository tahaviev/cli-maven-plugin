package com.github.tahaviev.cli.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Transformed object factory.
 *
 * @param <T> source object type
 * @param <R> result object type
 */
public final class Transformed<T, R> extends Delegated<R> {

    /**
     * Constructor.
     *
     * @param origin origin factory
     * @param transformation transformation
     */
    public Transformed(
        final Supplier<? extends T> origin,
        final Function<? super T, ? extends R> transformation
    ) {
        super(() -> transformation.apply(origin.get()));
    }

}

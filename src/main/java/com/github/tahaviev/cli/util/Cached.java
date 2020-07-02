package com.github.tahaviev.cli.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Cached object factory.
 *
 * @param <T> result type
 */
public final class Cached<T> extends Delegated<T> {

    /**
     * Constructor.
     *
     * @param origin origin
     * @param cache cache
     */
    public Cached(
        final Supplier<? extends T> origin, final AtomicReference<T> cache
    ) {
        super(
            () -> cache.updateAndGet(
                old -> Objects.requireNonNullElseGet(old, origin)
            )
        );
    }

    /**
     * Constructor.
     *
     * @param origin origin
     */
    public Cached(final Supplier<? extends T> origin) {
        this(origin, new AtomicReference<>());
    }

}

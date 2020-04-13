package com.github.tahaviev.cli.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public final class Cached<T> extends Delegated<T> {

    public Cached(
        final Supplier<? extends T> origin,
        final AtomicReference<T> cache
    ) {
        super(
            () -> cache.updateAndGet(
                old -> Objects.requireNonNullElseGet(old, origin)
            )
        );
    }

    public Cached(final Supplier<? extends T> origin) {
        this(origin, new AtomicReference<>());
    }

}

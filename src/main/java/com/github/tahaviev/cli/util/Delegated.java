package com.github.tahaviev.cli.util;

import java.util.function.Supplier;

/**
 * Delegated object factory.
 *
 * @param <T> result type
 */
public abstract class Delegated<T> implements Supplier<T> {

    /**
     * Delegate.
     */
    private final Supplier<? extends T> delegate;

    /**
     * Constructor.
     *
     * @param delegate {@link #delegate}
     */
    protected Delegated(final Supplier<? extends T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public final T get() {
        return this.delegate.get();
    }

}

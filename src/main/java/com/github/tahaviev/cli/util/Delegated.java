package com.github.tahaviev.cli.util;

import java.util.function.Supplier;

public abstract class Delegated<T> implements Supplier<T> {

    private final Supplier<? extends T> delegate;

    protected Delegated(final Supplier<? extends T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public final T get() {
        return this.delegate.get();
    }

}

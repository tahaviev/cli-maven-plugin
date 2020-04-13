package com.github.tahaviev.cli.util;

public abstract class RunnableDelegated implements Runnable {

    private final Runnable delegate;

    protected RunnableDelegated(final Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public final void run() {
        this.delegate.run();
    }

}

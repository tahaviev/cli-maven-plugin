package com.github.tahaviev.cli.util;

/**
 * Delegated runnable.
 */
public abstract class RunnableDelegated implements Runnable {

    /**
     * Delegate.
     */
    private final Runnable delegate;

    /**
     * Constructor.
     *
     * @param delegate {@link #delegate}
     */
    protected RunnableDelegated(final Runnable delegate) {
        this.delegate = delegate;
    }

    @Override
    public final void run() {
        this.delegate.run();
    }

}

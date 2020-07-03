package com.github.tahaviev.cli.matchers;

import java.util.function.Function;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Java object, which call result behaves like the provided matcher.
 *
 * @param <T> object type
 * @param <U> result call type
 */
public final class OnCall<T, U> extends FeatureMatcher<T, U> {

    /**
     * Call.
     */
    private final Function<? super T, ? extends U> call;

    /**
     * Constructor.
     *
     * @param call {@link #call}
     * @param delegate call result matcher
     */
    public OnCall(
        final Function<? super T, ? extends U> call,
        final Matcher<? super U> delegate
    ) {
        super(delegate, "result of method call", "result");
        this.call = call;
    }

    @Override
    public U featureValueOf(final T actual) {
        return this.call.apply(actual);
    }

}

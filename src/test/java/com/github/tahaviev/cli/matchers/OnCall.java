package com.github.tahaviev.cli.matchers;

import java.util.function.Function;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public final class OnCall<T, U> extends FeatureMatcher<T, U> {

    private final Function<? super T, ? extends U> call;

    public OnCall(
        final Function<? super T, ? extends U> call,
        final Matcher<? super U> delegate
    ) {
        super(delegate, "result of method call", "result");
        this.call = call;
    }

    @Override
    protected U featureValueOf(final T actual) {
        return this.call.apply(actual);
    }

}

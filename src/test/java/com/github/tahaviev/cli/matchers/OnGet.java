package com.github.tahaviev.cli.matchers;

import java.util.function.Supplier;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public final class OnGet<T> extends FeatureMatcher<Supplier<? extends T>, T> {

    public OnGet(final Matcher<? super T> delegate) {
        super(delegate, "", "");
    }

    @Override
    protected T featureValueOf(final Supplier<? extends T> actual) {
        return actual.get();
    }

}

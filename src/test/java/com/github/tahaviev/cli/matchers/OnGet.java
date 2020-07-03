package com.github.tahaviev.cli.matchers;

import java.util.function.Supplier;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Object factory, which produce objects, which behaves like the provided matcher.
 *
 * @param <T> object type
 */
public final class OnGet<T> extends FeatureMatcher<Supplier<? extends T>, T> {

    /**
     * Constructor.
     *
     * @param delegate matcher for produced objects
     */
    public OnGet(final Matcher<? super T> delegate) {
        super(delegate, "", "");
    }

    @Override
    public T featureValueOf(final Supplier<? extends T> actual) {
        return actual.get();
    }

}

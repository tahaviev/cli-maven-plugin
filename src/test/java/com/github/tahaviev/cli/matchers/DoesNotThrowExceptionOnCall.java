package com.github.tahaviev.cli.matchers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class DoesNotThrowExceptionOnCall<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Consumer<? super T> call;

    public DoesNotThrowExceptionOnCall(final Consumer<? super T> call) {
        this.call = call;
    }

    @Override
    protected boolean matchesSafely(final T item, final Description mismatch) {
        try {
            this.call.accept(item);
        } catch (final Exception ex) {
            final var writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            mismatch.appendText("exception was thrown:\n").appendText(writer.toString());
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("does not throw an exception");
    }

}

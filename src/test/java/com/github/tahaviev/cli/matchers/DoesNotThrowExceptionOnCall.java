package com.github.tahaviev.cli.matchers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Java object, which does not throw an exception on provided call.
 *
 * @param <T> object type
 */
@SuppressWarnings("PMD.AvoidCatchingGenericException")
public final class DoesNotThrowExceptionOnCall<T> extends TypeSafeDiagnosingMatcher<T> {

    /**
     * Call.
     */
    private final Consumer<? super T> call;

    /**
     * Constructor.
     *
     * @param call {@link #call}
     */
    public DoesNotThrowExceptionOnCall(final Consumer<? super T> call) {
        this.call = call;
    }

    @Override
    protected boolean matchesSafely(final T item, final Description mismatch) {
        boolean result;
        try {
            this.call.accept(item);
            result = true;
        } catch (final Exception ex) {
            final var writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            mismatch.appendText("exception was thrown:\n").appendText(writer.toString());
            result = false;
        }
        return result;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("does not throw an exception");
    }

}

package com.github.tahaviev.cli.util;

import com.github.tahaviev.cli.matchers.OnGet;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

/**
 * {@link StringsDividedByLengthAndDelimiter} tests.
 */
public final class StringsDividedByLengthAndDelimiterTest {

    /**
     * Can divide input with large length.
     */
    @Test
    public void divideInputWithLargeLength() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo bar", 5, ' '),
            new OnGet<>(
                hasSize(2)
            )
        );
    }

    /**
     * Cannot divide input with small length.
     */
    @Test
    public void dontDivideInputWithSmallLength() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo bar", 10, ' '),
            new OnGet<>(
                hasSize(1)
            )
        );
    }

    /**
     * Cannot divide large input.
     */
    @Test
    public void dontDivideLargeInput() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo largeWord bar", 5, ' '),
            new OnGet<>(
                contains("foo", "largeWord", "bar")
            )
        );
    }

    /**
     * Cannot divide last large word.
     */
    @Test
    public void dontDivideLastLargeWord() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo largeWord", 5, ' '),
            new OnGet<>(
                hasSize(2)
            )
        );
    }
}

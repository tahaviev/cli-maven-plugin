package com.github.tahaviev.cli.util;

import com.github.tahaviev.cli.matchers.OnGet;
import java.util.Collection;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public final class StringsDividedByLengthAndDelimiterTest {

    @Test
    public void divideInputOnLargeLength() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo bar", 5, ' '),
            new OnGet<>(
                hasSize(2)
            )
        );
    }

    @Test
    public void dontDivideInputOnSmallLength() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo bar", 10, ' '),
            new OnGet<>(
                hasSize(1)
            )
        );
    }

    @Test
    public void dontDivideLargeString() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo largeWord bar", 5, ' '),
            new OnGet<>(
                contains("foo", "largeWord", "bar")
            )
        );
    }

    @Test
    public void dontDivideLastLargeString() {
        assertThat(
            new StringsDividedByLengthAndDelimiter("foo largeWord", 5, ' '),
            new OnGet<>(
                hasSize(2)
            )
        );
    }
}

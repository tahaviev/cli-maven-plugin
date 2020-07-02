package com.github.tahaviev.cli.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Input stream from a string.
 */
public final class InputFromString extends Delegated<InputStream> {

    /**
     * Constructor.
     *
     * @param string string
     */
    public InputFromString(final Supplier<String> string) {
        super(
            () -> new ByteArrayInputStream(string.get().getBytes())
        );
    }

}

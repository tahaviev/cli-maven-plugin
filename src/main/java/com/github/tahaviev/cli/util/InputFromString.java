package com.github.tahaviev.cli.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

public final class InputFromString extends Delegated<InputStream> {

    public InputFromString(
        final Supplier<String> string
    ) {
        super(
            () -> new ByteArrayInputStream(string.get().getBytes())
        );
    }

}

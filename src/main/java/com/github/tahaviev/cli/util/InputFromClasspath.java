package com.github.tahaviev.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Supplier;

public final class InputFromClasspath extends Delegated<InputStream> {

    public InputFromClasspath(final String name, final Supplier<? extends ClassLoader> loader) {
        super(
            () -> {
                try {
                    return Objects
                        .requireNonNull(
                            loader.get().getResource(name),
                            () -> "cannot find resource: " + name
                        )
                        .openStream();
                } catch (final IOException ex) {
                    throw new RuntimeException(
                        "cannot open stream from resource: " + name, ex
                    );
                }
            }
        );
    }

    public InputFromClasspath(final String name, final Class<?> clazz) {
        this(name, clazz::getClassLoader);
    }

    public InputFromClasspath(final String name) {
        this(name, InputFromClasspath.class);
    }

}

package com.github.tahaviev.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Input stream from classpath file.
 */
public final class InputFromClasspath extends Delegated<InputStream> {

    /**
     * Constructor.
     *
     * @param name file name
     * @param loader class loader
     */
    public InputFromClasspath(
        final String name, final Supplier<? extends ClassLoader> loader
    ) {
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

    /**
     * Constructor.
     *
     * @param name file name
     * @param clazz class to obtain class loader
     */
    public InputFromClasspath(final String name, final Class<?> clazz) {
        this(name, clazz::getClassLoader);
    }

    /**
     * Constructor.
     *
     * @param name file name
     */
    public InputFromClasspath(final String name) {
        this(name, InputFromClasspath.class);
    }

}

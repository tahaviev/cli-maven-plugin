package com.github.tahaviev.cli.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Input stream from a file.
 */
public final class InputFromFile extends Delegated<InputStream> {

    /**
     * Constructor.
     *
     * @param file file
     */
    public InputFromFile(final Supplier<? extends Path> file) {
        super(
            () -> {
                try {
                    return new BufferedInputStream(
                        Files.newInputStream(file.get())
                    );
                } catch (final IOException ex) {
                    throw new RuntimeException("cannot open file " + file, ex);
                }
            }
        );
    }

    /**
     * Constructor.
     *
     * @param file file
     */
    public InputFromFile(final File file) {
        this(file::toPath);
    }

}

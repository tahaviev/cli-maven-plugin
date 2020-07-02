package com.github.tahaviev.cli.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Output stream from a file.
 */
public final class OutputFromFile extends Delegated<OutputStream> {

    /**
     * Constructor.
     *
     * @param file file
     */
    public OutputFromFile(final Supplier<? extends Path> file) {
        super(
            () -> {
                try {
                    return new BufferedOutputStream(
                        Files.newOutputStream(file.get())
                    );
                } catch (final IOException ex) {
                    throw new RuntimeException("cannot open file " + file, ex);
                }
            }
        );
    }

}

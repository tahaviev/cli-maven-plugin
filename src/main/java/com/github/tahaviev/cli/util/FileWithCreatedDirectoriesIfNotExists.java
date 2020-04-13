package com.github.tahaviev.cli.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * File with created directories if not exists.
 */
public final class FileWithCreatedDirectoriesIfNotExists extends Delegated<Path> {

    /**
     * Constructor.
     *
     * @param origin file
     */
    public FileWithCreatedDirectoriesIfNotExists(final Supplier<? extends Path> origin) {
        super(
            () -> {
                final var file = origin.get();
                try {
                    Files.createDirectories(file.getParent());
                } catch (final IOException ex) {
                    throw new RuntimeException("cannot create directory " + file.getParent(), ex);
                }
                return file;
            }
        );
    }

}

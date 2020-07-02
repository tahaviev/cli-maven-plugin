package com.github.tahaviev.cli.util;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Resolved file.
 */
public final class FileResolved extends Delegated<Path> {

    /**
     * Constructor.
     *
     * @param parent parent file
     * @param child child file name
     */
    public FileResolved(
        final Supplier<? extends Path> parent, final Supplier<String> child
    ) {
        super(
            () -> parent.get().resolve(child.get())
        );
    }

    /**
     * Constructor.
     *
     * @param parent parent file
     * @param child child file name
     */
    public FileResolved(final File parent, final Supplier<String> child) {
        this(parent::toPath, child);
    }

}

package com.github.tahaviev.cli.util;

import java.io.File;
import java.util.function.Supplier;

public final class FileResolved extends Delegated<File> {

    public FileResolved(
        final Supplier<? extends File> parent, final Supplier<String> child
    ) {
        super(
            () -> new File(parent.get(), child.get())
        );
    }

    public FileResolved(final File parent, final Supplier<String> child) {
        this(() -> parent, child);
    }

}

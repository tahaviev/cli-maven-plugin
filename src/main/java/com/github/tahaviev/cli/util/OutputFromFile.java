package com.github.tahaviev.cli.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Supplier;

public final class OutputFromFile extends Delegated<OutputStream> {

    public OutputFromFile(final Supplier<? extends File> supplier) {
        super(
            () -> {
                final var file = supplier.get();
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    return new BufferedOutputStream(
                        new FileOutputStream(file)
                    );
                } catch (final IOException ex) {
                    throw new RuntimeException("cannot open file " + file, ex);
                }
            }
        );
    }

}

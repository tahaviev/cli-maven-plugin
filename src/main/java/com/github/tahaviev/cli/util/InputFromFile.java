package com.github.tahaviev.cli.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class InputFromFile extends Delegated<InputStream> {

    public InputFromFile(final File file) {
        super(
            () -> {
                try {
                    return new BufferedInputStream(
                        new FileInputStream(file)
                    );
                } catch (final FileNotFoundException ex) {
                    throw new RuntimeException("cannot open file " + file, ex);
                }
            }
        );
    }

}

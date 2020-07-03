package com.github.tahaviev.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * Writing process.
 */
public final class Writing extends RunnableDelegated {

    /**
     * Constructor.
     *
     * @param input input stream
     * @param output output stream
     */
    public Writing(
        final Supplier<? extends InputStream> input,
        final Supplier<? extends OutputStream> output
    ) {
        super(
            () -> {
                try (var in = input.get(); var out = output.get()) {
                    in.transferTo(out);
                } catch (final IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        );
    }

}

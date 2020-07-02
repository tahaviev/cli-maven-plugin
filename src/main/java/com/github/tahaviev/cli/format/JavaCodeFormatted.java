package com.github.tahaviev.cli.format;

import com.github.tahaviev.cli.util.Delegated;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import java.util.function.Supplier;

/**
 * Formatted java code.
 */
public final class JavaCodeFormatted extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param code code
     */
    public JavaCodeFormatted(final Supplier<String> code) {
        super(
            () -> {
                try {
                    return new Formatter().formatSource(code.get());
                } catch (final FormatterException ex) {
                    throw new RuntimeException(ex);
                }
            }
        );
    }

}

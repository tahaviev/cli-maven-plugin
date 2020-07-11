package com.github.tahaviev.cli.code;

import com.github.tahaviev.cli.util.Delegated;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import java.util.function.Supplier;

/**
 * Formatted java code.
 */
public final class CodeFormatted extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param code code
     */
    public CodeFormatted(final Supplier<String> code) {
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

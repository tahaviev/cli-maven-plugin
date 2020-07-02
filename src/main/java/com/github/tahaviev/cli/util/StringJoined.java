package com.github.tahaviev.cli.util;

/**
 * Joined string.
 */
public final class StringJoined extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param strings strings
     */
    public StringJoined(final String... strings) {
        super(() -> String.join("", strings));
    }

}

package com.github.tahaviev.cli.util;

public final class StringJoined extends Delegated<String> {

    public StringJoined(final String... strings) {
        super(() -> String.join("", strings));
    }

}

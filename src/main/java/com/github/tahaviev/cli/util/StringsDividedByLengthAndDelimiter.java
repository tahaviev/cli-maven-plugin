package com.github.tahaviev.cli.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class StringsDividedByLengthAndDelimiter extends Delegated<List<String>> {

    public StringsDividedByLengthAndDelimiter(
        final Supplier<String> input, final int length, final char delimiter
    ) {
        super(
            () -> {
                final var result = new ArrayList<String>();
                var current = input.get();
                while (true) {
                    if (current.length() < length) {
                        result.add(current);
                        break;
                    }
                    var end = current.substring(0, length).lastIndexOf(delimiter);
                    if (end < 0) {
                        end = current.indexOf(delimiter);
                    }
                    if (end < 0) {
                        result.add(current);
                        break;
                    }
                    result.add(current.substring(0, end));
                    current = current.substring(end + 1);
                }
                return Collections.unmodifiableList(result);
            }
        );
    }

    public StringsDividedByLengthAndDelimiter(
        final String input, final int length, final char delimiter
    ) {
        this(() -> input, length, delimiter);
    }

}

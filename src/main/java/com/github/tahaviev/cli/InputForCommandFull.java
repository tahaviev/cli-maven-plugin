package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Full command input.
 */
public final class InputForCommandFull extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param command command
     * @param ancestors command ancestors
     */
    public InputForCommandFull(
        final Command command,
        final Supplier<? extends List<Command>> ancestors
    ) {
        super(
            () -> Stream
                .concat(
                    ancestors.get().stream(),
                    Stream.of(command)
                )
                .map(it -> Objects.requireNonNullElseGet(it.getInput(), it::getName))
                .collect(Collectors.joining(" "))
        );
    }

}

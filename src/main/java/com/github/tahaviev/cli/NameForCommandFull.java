package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Full command name.
 */
public final class NameForCommandFull extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param command command
     * @param ancestors command ancestors
     */
    public NameForCommandFull(
        final Command command,
        final Supplier<? extends List<Command>> ancestors
    ) {
        super(
            () -> Stream
                .concat(
                    ancestors.get().stream(),
                    Stream.of(command)
                )
                .map(Command::getName)
                .collect(Collectors.joining(" "))
        );
    }

}

package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Non-required options.
 */
public final class OptionsOptional extends Delegated<List<Command.Options.Option>> {

    /**
     * Constructor.
     *
     * @param options options
     */
    public OptionsOptional(
        final List<? extends Command.Options.Option> options
    ) {
        super(
            () -> options
                .stream()
                .filter(Predicate.not(Command.Options.Option::isRequired))
                .collect(Collectors.toUnmodifiableList())
        );
    }

}

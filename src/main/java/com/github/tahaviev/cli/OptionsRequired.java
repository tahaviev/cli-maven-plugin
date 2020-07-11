package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Required options.
 */
public final class OptionsRequired extends Delegated<List<Command.Options.Option>> {

    /**
     * Constructor.
     *
     * @param options options
     */
    public OptionsRequired(
        final List<? extends Command.Options.Option> options
    ) {
        super(
            () -> options
                .stream()
                .filter(Command.Options.Option::isRequired)
                .collect(Collectors.toUnmodifiableList())
        );
    }

}

package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Objects;

/**
 * Command input.
 */
public final class InputForCommand extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param command command
     */
    public InputForCommand(final Command command) {
        super(
            () -> Objects.requireNonNullElseGet(command.getInput(), command::getName)
        );
    }

}

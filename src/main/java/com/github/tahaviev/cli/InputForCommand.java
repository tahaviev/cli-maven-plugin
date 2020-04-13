package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Objects;

public final class InputForCommand extends Delegated<String> {

    public InputForCommand(final Command command) {
        super(
            () -> Objects.requireNonNullElseGet(command.getInput(), command::getName)
        );
    }

}

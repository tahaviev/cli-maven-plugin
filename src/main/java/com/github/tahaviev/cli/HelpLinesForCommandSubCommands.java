package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class HelpLinesForCommandSubCommands extends Delegated<List<String>> {

    public HelpLinesForCommandSubCommands(
        final Supplier<? extends Command> supplier,
        final int nameWidth
    ) {
        super(
            () -> {
                final var result = new ArrayList<String>();
                final var commands = supplier.get().getCommands();
                if (commands != null) {
                    for (final var command : commands.getCommand()) {
                        result.add(
                            String.format(
                                "%-" + nameWidth + "s%s",
                                "  " + command.getName() + ' ',
                                command.getDescription()
                            )
                        );
                    }
                }
                return Collections.unmodifiableList(result);
            }
        );
    }

    public HelpLinesForCommandSubCommands(final Command command) {
        this(() -> command, 15);
    }

}

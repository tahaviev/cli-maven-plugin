package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class HelpUsageForCommand extends Delegated<String> {

    public HelpUsageForCommand(
        final Supplier<? extends Command> supplier,
        final Supplier<? extends List<Command>> ancestors
    ) {
        super(
            () -> {
                final var builder = new StringBuilder();
                final var command = supplier.get();
                builder
                    .append(
                        ancestors
                            .get()
                            .stream()
                            .map(ancestor -> new InputForCommand(ancestor).get() + ' ')
                            .collect(Collectors.joining())
                    )
                    .append(new InputForCommand(command).get());
                if (command.getOptions() != null) {
                    builder.append(" <OPTIONS>");
                }
                if (command.getArgument() != null) {
                    final var name = command.getArgument().getName().toUpperCase();
                    builder.append(' ').append(name);
                    if (command.getArgument().isMultiple()) {
                        builder.append(" [").append(name).append("...]");
                    }
                }
                return builder.toString();
            }
        );
    }

    public static final class FromAncestorMap extends Delegated<String> {

        public FromAncestorMap(
            final Supplier<? extends Command> supplier,
            final Supplier<? extends Map<Command, List<Command>>> ancestors
        ) {
            super(
                new HelpUsageForCommand(
                    supplier,
                    () -> ancestors.get().getOrDefault(supplier.get(), Collections.emptyList())
                )
            );
        }

        public FromAncestorMap(
            final Command command,
            final Supplier<? extends Map<Command, List<Command>>> ancestors
        ) {
            this(() -> command, ancestors);
        }

        public FromAncestorMap(final Supplier<? extends Command> supplier) {
            this(supplier, new MappingCommandToAncestors(supplier));
        }

    }

}

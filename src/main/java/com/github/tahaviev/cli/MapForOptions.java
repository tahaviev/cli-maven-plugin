package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MapForOptions<V> extends Delegated<Map<Command.Options.Option, V>> {

    public MapForOptions(
        final Supplier<? extends Command> root,
        final Function<? super Command.Options.Option, ? extends Supplier<V>> function
    ) {
        super(
            () -> {
                final var result = new HashMap<Command.Options.Option, V>();
                MapForOptions.walk(root.get(), function, result);
                return Collections.unmodifiableMap(result);
            }
        );
    }

    private static <V> void walk(
        final Command command,
        final Function<? super Command.Options.Option, ? extends Supplier<V>> function,
        final Map<Command.Options.Option, V> result
    ) {
        if (command.getOptions() != null) {
            for (final var option : command.getOptions().getOption()) {
                result.put(option, function.apply(option).get());
            }
        }
        if (command.getCommands() == null) {
            return;
        }
        for (final var child : command.getCommands().getCommand()) {
            MapForOptions.walk(child, function, result);
        }
    }

}

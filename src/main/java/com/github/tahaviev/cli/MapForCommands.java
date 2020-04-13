package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MapForCommands<V> extends Delegated<Map<Command, V>> {

    public MapForCommands(
        final Supplier<? extends Command> root,
        final Function<? super Command, ? extends Supplier<V>> function
    ) {
        super(
            () -> {
                final var result = new HashMap<Command, V>();
                MapForCommands.walk(root.get(), function, result);
                return Collections.unmodifiableMap(result);
            }
        );
    }

    private static <V> void walk(
        final Command command,
        final Function<? super Command, ? extends Supplier<V>> function,
        final Map<Command, V> result
    ) {
        result.put(command, function.apply(command).get());
        if (command.getCommands() == null) {
            return;
        }
        for (final var child : command.getCommands().getCommand()) {
            MapForCommands.walk(child, function, result);
        }
    }

}

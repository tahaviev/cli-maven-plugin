package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Map for commands.
 *
 * @param <V> map value type
 */
public final class MapForCommands<V> extends Delegated<Map<Command, V>> {

    /**
     * Constructor.
     *
     * @param root command root factory
     * @param function function from a command to a map value
     */
    public MapForCommands(
        final Supplier<? extends Command> root,
        final Function<? super Command, ? extends Supplier<V>> function
    ) {
        super(
            () -> {
                final var result = new HashMap<Command, V>();
                final var deque = new ArrayDeque<Command>();
                deque.push(root.get());
                while (!deque.isEmpty()) {
                    final var command = deque.pop();
                    result.put(command, function.apply(command).get());
                    if (command.getCommands() != null) {
                        for (final var child : command.getCommands().getCommand()) {
                            deque.push(child);
                        }
                    }
                }
                return Collections.unmodifiableMap(result);
            }
        );
    }

}

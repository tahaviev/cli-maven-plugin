package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Mapping between command and their ancestors.
 */
public final class MappingCommandToAncestors extends Delegated<Map<Command, List<Command>>> {

    /**
     * Constructor.
     *
     * @param root root command factory.
     */
    public MappingCommandToAncestors(final Supplier<? extends Command> root) {
        super(
            () -> {
                final var result = new HashMap<Command, List<Command>>();
                final var deque = new ArrayDeque<Command>();
                deque.push(root.get());
                while (!deque.isEmpty()) {
                    final var command = deque.pop();
                    if (command.getCommands() != null) {
                        for (final var child : command.getCommands().getCommand()) {
                            final var ancestors = new ArrayList<Command>();
                            ancestors.addAll(
                                Objects.requireNonNullElseGet(
                                    result.get(command), Collections::emptyList
                                )
                            );
                            ancestors.add(command);
                            result.put(child, Collections.unmodifiableList(ancestors));
                            deque.push(child);
                        }
                    }
                }
                return Collections.unmodifiableMap(result);
            }
        );
    }

}

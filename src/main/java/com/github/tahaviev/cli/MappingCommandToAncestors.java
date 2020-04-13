package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class MappingCommandToAncestors extends Delegated<Map<Command, List<Command>>> {

    public MappingCommandToAncestors(final Supplier<? extends Command> root) {
        super(
            () -> {
                final var result = new HashMap<Command, List<Command>>();
                MappingCommandToAncestors.walk(root.get(), result);
                return Collections.unmodifiableMap(result);
            }
        );
    }

    private static void walk(
        final Command parent,
        final Map<? super Command, List<Command>> result
    ) {
        if (parent.getCommands() == null) {
            return;
        }
        for (final var child : parent.getCommands().getCommand()) {
            final var ancestors = new ArrayList<Command>();
            ancestors.addAll(
                Objects.requireNonNullElseGet(result.get(parent), Collections::emptyList)
            );
            ancestors.add(parent);
            result.put(child, Collections.unmodifiableList(ancestors));
            MappingCommandToAncestors.walk(child, result);
        }
    }

}

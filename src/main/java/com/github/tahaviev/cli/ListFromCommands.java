package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * List created from all commands.
 *
 * @param <E> list element type
 */
public final class ListFromCommands<E> extends Delegated<List<E>> {

    /**
     * Constructor.
     *
     * @param root root command
     * @param function transformation
     */
    public ListFromCommands(
        final Supplier<? extends Command> root,
        final Function<? super Command, ? extends Supplier<? extends E>> function
    ) {
        super(
            () -> {
                final var result = new ArrayList<E>();
                final var deque = new ArrayDeque<Command>();
                deque.add(root.get());
                while (!deque.isEmpty()) {
                    final var command = deque.pop();
                    result.add(function.apply(command).get());
                    if (command.getCommands() != null) {
                        deque.addAll(command.getCommands().getCommand());
                    }
                }
                return Collections.unmodifiableList(result);
            }
        );
    }

}

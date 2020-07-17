package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;

/**
 * Full qualified class name for a command.
 */
public final class FullClassNameForCommand extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param command command factory
     * @param ancestors command ancestors
     */
    public FullClassNameForCommand(
        final Supplier<? extends Command> command,
        final Supplier<? extends List<Command>> ancestors
    ) {
        super(
            () -> Stream
                .concat(ancestors.get().stream(), Stream.of(command.get()))
                .map(it -> StringUtils.capitalize(it.getName()))
                .collect(Collectors.joining("."))
        );
    }

    /**
     * Constructor.
     *
     * @param command command
     * @param ancestors command ancestors
     */
    public FullClassNameForCommand(
        final Command command,
        final Supplier<? extends List<Command>> ancestors
    ) {
        this(() -> command, ancestors);
    }

    /**
     * Full qualified class name for a command.
     */
    public static final class FromAncestorMap extends Delegated<String> {

        /**
         * Constructor.
         *
         * @param command command
         * @param ancestors ancestors map
         */
        public FromAncestorMap(
            final Command command,
            final Supplier<? extends Map<Command, List<Command>>> ancestors
        ) {
            super(
                new FullClassNameForCommand(
                    command,
                    () -> ancestors.get().getOrDefault(command, Collections.emptyList())
                )
            );
        }

    }

}

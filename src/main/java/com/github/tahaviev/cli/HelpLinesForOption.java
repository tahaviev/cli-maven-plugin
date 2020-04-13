package com.github.tahaviev.cli;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Delegated;
import com.github.tahaviev.cli.util.StringsDividedByLengthAndDelimiter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class HelpLinesForOption extends Delegated<List<String>> {

    public HelpLinesForOption(
        final Supplier<? extends Command.Options.Option> supplier,
        final int width,
        final int keysWidth
    ) {
        super(
            () -> {
                final var result = new ArrayList<String>();
                final var option = supplier.get();
                final var descriptions = new StringsDividedByLengthAndDelimiter(
                    String.join(
                        "",
                        option.getDescription(),
                        Optional
                            .ofNullable(option.getDefault())
                            .map(it -> String.format(" (default %s)", it))
                            .orElse("")
                    ),
                    width - keysWidth,
                    ' '
                ).get();
                result.add(
                    String.format(
                        "%5s%-" + (keysWidth - 5) + "s%s",
                        Optional
                            .ofNullable(option.getShort())
                            .map(name -> '-' + name + ',')
                            .orElse(""),
                        String.format(
                            " --%s %s",
                            option.getName(),
                            Optional
                                .of(option.getType())
                                .filter(Predicate.not("boolean"::equals))
                                .orElse("")
                        ),
                        descriptions.get(0)
                    )
                );
                if (descriptions.size() > 1) {
                    for (final var line : descriptions.subList(1, descriptions.size())) {
                        result.add(
                            String.format("%" + keysWidth + "s%s", "", line)
                        );
                    }
                }
                return Collections.unmodifiableList(result);
            }
        );
    }

    public HelpLinesForOption(final Supplier<? extends Command.Options.Option> supplier) {
        this(supplier, 80, 40);
    }

    public HelpLinesForOption(final Command.Options.Option option) {
        this(() -> option);
    }

}

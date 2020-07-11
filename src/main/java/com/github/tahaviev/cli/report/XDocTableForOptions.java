package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.models.xdoc.Table;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * XDoc table for options.
 */
public final class XDocTableForOptions extends Delegated<Table> {

    /**
     * Constructor.
     *
     * @param options options
     */
    public XDocTableForOptions(final List<? extends Command.Options.Option> options) {
        super(
            () -> {
                final var result = new Table();
                result.getColgroup().add(
                    new XDocColgroupForTable(20, 10, 20, 50).get()
                );
                result.getTr().add(
                    new XDocTrForTableHeader("Name, shorthand", "Type", "Default", "Description")
                        .get()
                );
                for (final var option : options) {
                    result.getTr().add(
                        new XDocTrForTableBody(
                            String.format(
                                "--%s%s",
                                option.getName(),
                                Optional
                                    .ofNullable(option.getShort())
                                    .map(it -> " , -" + it)
                                    .orElse("")
                            ),
                            option.getType(),
                            Objects.requireNonNullElse(option.getDefault(), ""),
                            option.getDescription()
                        ).get()
                    );
                }
                return result;
            }
        );
    }

}

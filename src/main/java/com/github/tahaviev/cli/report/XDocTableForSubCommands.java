package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.models.xdoc.Table;
import com.github.tahaviev.cli.util.Delegated;

/**
 * XDoc table for command subcommands.
 */
public final class XDocTableForSubCommands extends Delegated<Table> {

    /**
     * Constructor.
     *
     * @param command command
     */
    public XDocTableForSubCommands(final Command command) {
        super(
            () -> {
                final var result = new Table();
                result.getColgroup().add(new XDocColgroupForTable(20, 80).get());
                result.getTr().add(
                    new XDocTrForTableHeader("Name", "Description").get()
                );
                for (final var subcommand : command.getCommands().getCommand()) {
                    result.getTr().add(
                        new XDocTrForTableBody(
                            subcommand.getName(), subcommand.getDescription()
                        ).get()
                    );
                }
                return result;
            }
        );
    }

}

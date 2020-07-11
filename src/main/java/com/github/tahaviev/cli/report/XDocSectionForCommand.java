package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.HelpUsageForCommand;
import com.github.tahaviev.cli.NameForCommandFull;
import com.github.tahaviev.cli.OptionsOptional;
import com.github.tahaviev.cli.OptionsRequired;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.models.xdoc.H4;
import com.github.tahaviev.cli.models.xdoc.P;
import com.github.tahaviev.cli.models.xdoc.Section;
import com.github.tahaviev.cli.models.xdoc.Source;
import com.github.tahaviev.cli.models.xdoc.Subsection;
import com.github.tahaviev.cli.util.Delegated;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * XDoc section for a command.
 */
@SuppressWarnings(
    {
        "PMD.DoubleBraceInitialization",
        "checkstyle:AvoidDoubleBraceInitialization",
        "checkstyle:Indentation"
    }
)
public final class XDocSectionForCommand extends Delegated<Section> {

    /**
     * Constructor.
     *
     * @param command command
     * @param ancestors ancestors
     */
    public XDocSectionForCommand(
        final Command command,
        final Supplier<? extends List<Command>> ancestors
    ) {
        super(
            () -> new Section() {{
                setName(new NameForCommandFull(command, ancestors).get());
                getPOrH1OrH2().add(
                    new P() {{
                        getContent().add(command.getDescription());
                    }}
                );
                getPOrH1OrH2().add(
                    new Subsection() {{
                        setName("Usage");
                        getPOrH1OrH2().add(
                            new Source() {{
                                getContent().add(
                                    new HelpUsageForCommand(command, ancestors).get()
                                );
                            }}
                        );
                    }}
                );
                if (command.getOptions() != null) {
                    getPOrH1OrH2().add(
                        new Subsection() {{
                            setName("Options");
                            final var required = new OptionsRequired(
                                command.getOptions().getOption()
                            ).get();
                            if (!required.isEmpty()) {
                                getPOrH1OrH2().add(
                                    new H4() {{
                                        getContent().add("Required");
                                    }}
                                );
                                getPOrH1OrH2().add(
                                    new XDocTableForOptions(required).get()
                                );
                            }
                            final var optional = new OptionsOptional(
                                command.getOptions().getOption()
                            ).get();
                            if (!optional.isEmpty()) {
                                getPOrH1OrH2().add(
                                    new H4() {{
                                        getContent().add("Optional");
                                    }}
                                );
                                getPOrH1OrH2().add(
                                    new XDocTableForOptions(optional).get()
                                );
                            }
                        }}
                    );
                }
                if (command.getCommands() != null) {
                    getPOrH1OrH2().add(
                        new Subsection() {{
                            setName("Commands");
                            getPOrH1OrH2().add(
                                new XDocTableForSubCommands(command).get()
                            );
                        }}
                    );
                }
            }}
        );
    }

    /**
     * XDoc section for a command.
     */
    public static final class FromAncestorMap extends Delegated<Section> {

        /**
         * Constructor.
         *
         * @param command command
         * @param ancestors command ancestors
         */
        public FromAncestorMap(
            final Command command,
            final Supplier<? extends Map<Command, List<Command>>> ancestors
        ) {
            super(
                new XDocSectionForCommand(
                    command,
                    () -> ancestors.get().getOrDefault(command, Collections.emptyList())
                )
            );
        }

    }

}

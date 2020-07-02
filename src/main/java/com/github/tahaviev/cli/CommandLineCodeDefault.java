package com.github.tahaviev.cli;

import com.github.tahaviev.cli.format.JavaCodeFormatted;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Cached;
import com.github.tahaviev.cli.util.ClassFromName;
import com.github.tahaviev.cli.util.Delegated;
import com.github.tahaviev.cli.util.InputFromClasspath;
import com.github.tahaviev.cli.util.JAXBUnmarshalledFromInput;
import com.github.tahaviev.cli.util.MapWith;
import com.github.tahaviev.cli.util.SideEffected;
import com.github.tahaviev.cli.util.XSDValidation;
import com.github.tahaviev.cli.velocity.VelocityEngineLazy;
import com.github.tahaviev.cli.velocity.VelocityTemplate;
import com.github.tahaviev.cli.velocity.VelocityTemplateMerged;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Default factory for command line code.
 */
public final class CommandLineCodeDefault extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param descriptor input stream for xml descriptor
     * @param configuration velocity context
     */
    public CommandLineCodeDefault(
        final Supplier<? extends InputStream> descriptor,
        final Supplier<? extends Map<String, Object>> configuration
    ) {
        super(
            () -> {
                final var root = new Cached<>(
                    new JAXBUnmarshalledFromInput<>(
                        new SideEffected<>(
                            new XSDValidation(
                                descriptor,
                                new InputFromClasspath("cli.xsd")
                            ),
                            descriptor
                        ),
                        Command.class
                    )
                );
                final var ancestors = new Cached<>(
                    new MappingCommandToAncestors(root)
                );
                return new JavaCodeFormatted(
                    new VelocityTemplateMerged(
                        new VelocityTemplate(
                            new VelocityEngineLazy(
                                new MapWith<>(
                                    new MapWith<>("resource.loader", "classpath"),
                                    "classpath.resource.loader.class",
                                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
                                )
                            ),
                            "command-line.vm"
                        ),
                        new MapWith<String, Object>(
                            new MapWith<String, Object>(
                                new MapWith<String, Object>(
                                    new MapWith<String, Object>(
                                        new MapWith<String, Object>(
                                            new MapWith<String, Object>(
                                                configuration,
                                                "StringUtils",
                                                new ClassFromName("org.apache.commons.lang.StringUtils")
                                            ),
                                            "commandToAncestors",
                                            ancestors
                                        ),
                                        "commandToHelpLinesForSubCommands",
                                        new MapForCommands<>(
                                            root,
                                            HelpLinesForCommandSubCommands::new
                                        )
                                    ),
                                    "commandToHelpUsage",
                                    new MapForCommands<>(
                                        root,
                                        command -> new HelpUsageForCommand.FromAncestorMap(command, ancestors)
                                    )
                                ),
                                "root",
                                root
                            ),
                            "optionToHelpLines",
                            new MapForOptions<>(root, HelpLinesForOption::new)
                        )
                    )
                ).get();
            }
        );
    }

}

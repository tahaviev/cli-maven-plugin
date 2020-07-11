package com.github.tahaviev.cli.code;

import com.github.tahaviev.cli.HelpLinesForCommandSubCommands;
import com.github.tahaviev.cli.HelpLinesForOption;
import com.github.tahaviev.cli.HelpUsageForCommand;
import com.github.tahaviev.cli.MapForCommands;
import com.github.tahaviev.cli.MapForOptions;
import com.github.tahaviev.cli.MappingCommandToAncestors;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.Cached;
import com.github.tahaviev.cli.util.ClassFromName;
import com.github.tahaviev.cli.util.Delegated;
import com.github.tahaviev.cli.util.InputFromClasspath;
import com.github.tahaviev.cli.util.JAXBObjectFromInput;
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
@SuppressWarnings("checkstyle:LineLength")
public final class CodeForCommandLine extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param descriptor input stream for xml descriptor
     * @param configuration velocity context
     */
    public CodeForCommandLine(
        final Supplier<? extends InputStream> descriptor,
        final Supplier<? extends Map<String, Object>> configuration
    ) {
        super(
            () -> {
                final var root = new Cached<>(
                    new JAXBObjectFromInput<>(
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
                return new CodeFormatted(
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

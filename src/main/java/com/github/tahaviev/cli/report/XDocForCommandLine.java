package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.ListFromCommands;
import com.github.tahaviev.cli.MappingCommandToAncestors;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.models.xdoc.Body;
import com.github.tahaviev.cli.models.xdoc.Document;
import com.github.tahaviev.cli.models.xdoc.Head;
import com.github.tahaviev.cli.models.xdoc.Title;
import com.github.tahaviev.cli.util.Cached;
import com.github.tahaviev.cli.util.Delegated;
import com.github.tahaviev.cli.util.InputFromClasspath;
import com.github.tahaviev.cli.util.JAXBObjectFromInput;
import com.github.tahaviev.cli.util.SideEffected;
import com.github.tahaviev.cli.util.XSDValidation;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Default factory for XDoc report.
 */
@SuppressWarnings(
    {
        "PMD.DoubleBraceInitialization",
        "checkstyle:AvoidDoubleBraceInitialization",
        "checkstyle:Indentation"
    }
)
public final class XDocForCommandLine extends Delegated<Document> {

    /**
     * Constructor.
     *
     * @param descriptor descriptor
     */
    public XDocForCommandLine(final Supplier<? extends InputStream> descriptor) {
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
                return new Document() {{
                    setHead(
                        new Head() {{
                            getContent().add(
                                new Title() {{
                                    setContent("CLI");
                                }}
                            );
                        }}
                    );
                    setBody(
                        new Body() {{
                            getSection().addAll(
                                new ListFromCommands<>(
                                    root,
                                    command -> new XDocSectionForCommand.FromAncestorMap(
                                        command, ancestors
                                    )
                                ).get()
                            );
                        }}
                    );
                }};
            }
        );
    }

}

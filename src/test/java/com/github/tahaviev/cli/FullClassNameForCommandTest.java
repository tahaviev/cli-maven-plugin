package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.OnGet;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.JAXBObjectFromInput;
import com.github.tahaviev.cli.util.StringJoined;
import com.github.tahaviev.cli.util.Transformed;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * {@link FullClassNameForCommand} tests.
 */
public final class FullClassNameForCommandTest {

    /**
     * Can produce correct full qualified class name.
     */
    @Test
    public void producesCorrectFullQualifiedClassName() {
        assertThat(
            new FullClassNameForCommand(
                new JAXBObjectFromInput.Text<>(
                    "<command name='foo'/>",
                    Command.class
                ),
                new Transformed<>(
                    new JAXBObjectFromInput.Text<>(
                        new StringJoined(
                            "<commands>",
                            "  <command name='bar'/>",
                            "  <command name='baz'/>",
                            "</commands>"
                        ),
                        Command.Commands.class
                    ),
                    Command.Commands::getCommand
                )
            ),
            new OnGet<>(
                equalTo("Bar.Baz.Foo")
            )
        );
    }
}

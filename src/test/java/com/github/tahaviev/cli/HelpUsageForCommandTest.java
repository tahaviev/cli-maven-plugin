package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.OnGet;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.JAXBUnmarshalledFromInput;
import com.github.tahaviev.cli.util.StringJoined;
import com.github.tahaviev.cli.util.Transformed;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * {@link HelpUsageForCommand} tests.
 */
public final class HelpUsageForCommandTest {

    /**
     * Can produce correct help for root command.
     */
    @Test
    public void producesCorrectHelpForRootCommand() {
        assertThat(
            new HelpUsageForCommand.FromAncestorMap(
                new JAXBUnmarshalledFromInput.Text<>(
                    new StringJoined(
                        "<command name='A' input='i'>",
                        "  <options/>",
                        "  <argument name='a' multiple='true'/>",
                        "</command>"
                    ),
                    Command.class
                )
            ),
            new OnGet<>(
                equalTo("i <OPTIONS> A [A...]")
            )
        );
    }

    /**
     * Can produce correct help for sub command.
     */
    @Test
    public void producesCorrectHelpForSubCommand() {
        assertThat(
            new HelpUsageForCommand(
                new JAXBUnmarshalledFromInput.Text<>(
                    "<command name='A'/>",
                    Command.class
                ),
                new Transformed<>(
                    new JAXBUnmarshalledFromInput.Text<>(
                        new StringJoined(
                            "<commands>",
                            "  <command name='B'/>",
                            "  <command name='C'/>",
                            "</commands>"
                        ),
                        Command.Commands.class
                    ),
                    Command.Commands::getCommand
                )
            ),
            new OnGet<>(
                equalTo("B C A")
            )
        );
    }

}

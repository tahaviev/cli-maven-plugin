package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.OnGet;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.JAXBUnmarshalledFromInput;
import com.github.tahaviev.cli.util.StringJoined;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.matchesRegex;

public final class HelpLinesForCommandSubCommandsTest {

    @Test
    public void producesCorrectLines() {
        assertThat(
            new HelpLinesForCommandSubCommands(
                new JAXBUnmarshalledFromInput.Text<>(
                    new StringJoined(
                        "<command name='A'>",
                        "  <commands>",
                        "    <command name='B' description='foo'/>",
                        "    <command name='C' description='bar'/>",
                        "  </commands>",
                        "</command>"
                    ),
                    Command.class
                ),
                10
            ),
            new OnGet<>(
                contains(
                    allOf(
                        matchesRegex("\\s{2}B\\s+foo"),
                        matchesRegex(".{10}foo")
                    ),
                    allOf(
                        matchesRegex("\\s{2}C\\s+bar"),
                        matchesRegex(".{10}bar")
                    )
                )
            )
        );
    }
}

package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.OnCall;
import com.github.tahaviev.cli.matchers.OnGet;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.JAXBObjectFromInput;
import com.github.tahaviev.cli.util.StringJoined;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

/**
 * {@link MappingCommandToAncestors} tests.
 */
public final class MappingCommandToAncestorsTest {

    /**
     * Can produce correct ancestors.
     */
    @Test
    public void producesCorrectAncestors() {
        assertThat(
            new MappingCommandToAncestors(
                new JAXBObjectFromInput.Text<>(
                    new StringJoined(
                        "<command name='A'>",
                        "  <commands>",
                        "    <command name='B'>",
                        "      <commands>",
                        "        <command name='C'/>",
                        "      </commands>",
                        "    </command>",
                        "  </commands>",
                        "</command>"
                    ),
                    Command.class
                )
            ),
            new OnGet<>(
                allOf(
                    aMapWithSize(2),
                    hasEntry(
                        new OnCall<>(Command::getName, equalTo("B")),
                        contains(
                            new OnCall<>(Command::getName, equalTo("A"))
                        )
                    ),
                    hasEntry(
                        new OnCall<>(Command::getName, equalTo("C")),
                        contains(
                            new OnCall<>(Command::getName, equalTo("A")),
                            new OnCall<>(Command::getName, equalTo("B"))
                        )
                    )
                )
            )
        );
    }

}

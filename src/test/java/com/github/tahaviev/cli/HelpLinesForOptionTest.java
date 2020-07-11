package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.OnGet;
import com.github.tahaviev.cli.models.Command;
import com.github.tahaviev.cli.util.JAXBObjectFromInput;
import java.util.Collection;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesRegex;

/**
 * {@link HelpLinesForOption} tests.
 */
public final class HelpLinesForOptionTest {

    /**
     * Can produce correct line.
     */
    @Test
    public void producesCorrectLine() {
        assertThat(
            new HelpLinesForOption(
                new JAXBObjectFromInput.Text<>(
                    "<option name='name' type='int' description='description'/>",
                    Command.Options.Option.class
                )
            ),
            new OnGet<>(
                Matchers.<Collection<String>>allOf(
                    hasSize(1),
                    hasItem(
                        allOf(
                            matchesRegex("\\s{6}--name int\\s+description"),
                            matchesRegex(".{40}description")
                        )
                    )
                )
            )
        );
    }

    /**
     * Can produce correct lines on long description.
     */
    @Test
    public void producesCorrectLinesOnLongDescription() {
        assertThat(
            new HelpLinesForOption(
                new JAXBObjectFromInput.Text<>(
                    "<option name='name' description='long description'/>",
                    Command.Options.Option.class
                ),
                30,
                20
            ),
            new OnGet<>(
                contains(
                    allOf(
                        matchesRegex("\\s+--name\\s+long"),
                        matchesRegex(".{20}long")
                    ),
                    matchesRegex("\\s{20}description")
                )
            )
        );
    }

}

package com.github.tahaviev.cli;

import com.github.tahaviev.cli.matchers.CompilesWithoutErrors;
import com.github.tahaviev.cli.matchers.DoesNotThrowExceptionOnCall;
import com.github.tahaviev.cli.util.Delegated;
import com.github.tahaviev.cli.util.InputFromClasspath;
import com.github.tahaviev.cli.util.MapWith;
import java.io.File;
import java.util.Map;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link CommandLineCodeDefault} tests.
 */
public final class CommandLineCodeDefaultTest {

    /**
     * Can be compiled without errors.
     *
     * @param output output for compiled classes
     */
    @Test
    public void compilesWithoutErrors(@TempDir final File output) {
        assertThat(
            new CommandLineCodeDefault(
                new InputFromClasspath("test.xml"),
                new CommandLineCodeDefaultTest.Config()
            ),
            new CompilesWithoutErrors("CommandLine", output)
        );
    }

    /**
     * Cannot throw an exception on valid input.
     */
    @Test
    public void doesNotThrowAnException() {
        assertThat(
            new CommandLineCodeDefault(
                new InputFromClasspath("test.xml"),
                new CommandLineCodeDefaultTest.Config()
            ),
            new DoesNotThrowExceptionOnCall<>(Supplier::get)
        );
    }

    /**
     * Test config.
     */
    private static final class Config extends Delegated<Map<String, Object>> {

        /**
         * Constructor.
         */
        public Config() {
            super(
                new MapWith<>(
                    new MapWith<>(
                        new MapWith<>("class", "CommandLine"),
                        "package",
                        "test"
                    ),
                    "version",
                    "1.0.0"
                )
            );
        }

    }

}

package com.github.tahaviev.cli.matchers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Java code, which compiles without errors.
 */
public final class CompilesWithoutErrors extends TypeSafeDiagnosingMatcher<Supplier<String>> {

    /**
     * Class name.
     */
    private final String name;

    /**
     * Output for compiled classes.
     */
    private final File output;

    /**
     * Constructor.
     *
     * @param name {@link #name}
     * @param output {@link #output}
     */
    public CompilesWithoutErrors(final String name, final File output) {
        this.name = name;
        this.output = output;
    }

    @Override
    protected boolean matchesSafely(final Supplier<String> code, final Description mismatch) {
        final var errors = new ArrayList<String>();
        final var compiler = ToolProvider.getSystemJavaCompiler();
        try (var manager = compiler.getStandardFileManager(null, null, null)) {
            try {
                manager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(this.output));
            } catch (final IOException ex) {
                throw new RuntimeException(
                    "cannot set location for class output: " + this.output,
                    ex
                );
            }
            compiler.getTask(
                null,
                manager,
                diagnostic -> errors.add(diagnostic.toString()),
                null,
                null,
                List.of(
                    new SimpleJavaFileObject(
                        URI.create("string:///" + this.name + ".java"),
                        JavaFileObject.Kind.SOURCE
                    ) {

                        @Override
                        public CharSequence getCharContent(final boolean encoding) {
                            return code.get();
                        }
                    }
                )
            ).call();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        if (!errors.isEmpty()) {
            mismatch.appendValueList("\n", "\n", "\n", errors);
        }
        return errors.isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("compiles without errors");
    }

}

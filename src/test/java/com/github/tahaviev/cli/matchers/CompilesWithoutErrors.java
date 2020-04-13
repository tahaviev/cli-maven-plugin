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

public final class CompilesWithoutErrors extends TypeSafeDiagnosingMatcher<Supplier<String>> {

    private final String name;
    private final File output;

    public CompilesWithoutErrors(final String name, final File output) {
        this.name = name;
        this.output = output;
    }

    @Override
    protected boolean matchesSafely(final Supplier<String> code, final Description mismatch) {
        final var compiler = ToolProvider.getSystemJavaCompiler();
        final var manager = compiler.getStandardFileManager(null, null, null);
        try {
            manager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(this.output));
        } catch (final IOException ex) {
            throw new RuntimeException("cannot set location for class output: " + this.output, ex);
        }
        final var errors = new ArrayList<String>();
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

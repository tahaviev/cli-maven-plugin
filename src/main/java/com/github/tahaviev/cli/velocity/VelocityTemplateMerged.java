package com.github.tahaviev.cli.velocity;

import com.github.tahaviev.cli.util.Delegated;
import java.io.StringWriter;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public final class VelocityTemplateMerged extends Delegated<String> {

    public VelocityTemplateMerged(
        final Supplier<? extends Template> template,
        final Supplier<? extends Map<? extends Object, ? extends Object>> context
    ) {
        super(
            () -> {
                final var writer = new StringWriter();
                template.get().merge(new VelocityContext(context.get()), writer);
                return writer.toString();
            }
        );
    }

}

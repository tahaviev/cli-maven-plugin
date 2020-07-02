package com.github.tahaviev.cli.velocity;

import com.github.tahaviev.cli.util.Delegated;
import java.util.function.Supplier;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

/**
 * Velocity template factory.
 */
public final class VelocityTemplate extends Delegated<Template> {

    /**
     * Constructor.
     *
     * @param engine engine
     * @param name name
     * @param encoding encoding
     */
    public VelocityTemplate(
        final Supplier<? extends VelocityEngine> engine,
        final String name,
        final String encoding
    ) {
        super(
            () -> engine.get().getTemplate(name, encoding)
        );
    }

    /**
     * Constructor.
     *
     * @param engine engine
     * @param name name
     */
    public VelocityTemplate(
        final Supplier<? extends VelocityEngine> engine, final String name
    ) {
        this(engine, name, "UTF-8");
    }

}

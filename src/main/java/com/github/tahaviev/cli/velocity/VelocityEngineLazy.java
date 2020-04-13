package com.github.tahaviev.cli.velocity;

import com.github.tahaviev.cli.util.Delegated;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;
import org.apache.velocity.app.VelocityEngine;

public final class VelocityEngineLazy extends Delegated<VelocityEngine> {

    public VelocityEngineLazy(final Supplier<? extends Map<Object, Object>> map) {
        super(
            () -> {
                final var properties = new Properties();
                properties.putAll(map.get());
                return new VelocityEngine(properties);
            }
        );
    }

}

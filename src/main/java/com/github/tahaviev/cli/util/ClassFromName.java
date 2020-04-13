package com.github.tahaviev.cli.util;

public final class ClassFromName extends Delegated<Class<?>> {

    public ClassFromName(final String name) {
        super(
            () -> {
                try {
                    return Class.forName(name);
                } catch (final ClassNotFoundException ex) {
                    throw new RuntimeException("cannot find class: " + name, ex);
                }
            }
        );
    }

}

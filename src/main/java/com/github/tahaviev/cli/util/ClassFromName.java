package com.github.tahaviev.cli.util;

/**
 * Class from name.
 */
public final class ClassFromName extends Delegated<Class<?>> {

    /**
     * Constructor.
     *
     * @param name name
     */
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

package com.github.tahaviev.cli.util;

/**
 * Directory name from java package name.
 */
public final class DirectoryNameFromPackage extends Delegated<String> {

    /**
     * Constructor.
     *
     * @param packageName package name
     */
    public DirectoryNameFromPackage(final String packageName) {
        super(
            () -> packageName.replaceAll("\\.", "/")
        );
    }

}

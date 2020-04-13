package com.github.tahaviev.cli.util;

public final class DirectoryNameFromPackage extends Delegated<String> {

    public DirectoryNameFromPackage(final String packageName) {
        super(
            () -> packageName.replaceAll("\\.", "/")
        );
    }

}

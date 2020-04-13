package com.github.tahaviev.cli.util;

import java.util.function.Function;
import java.util.function.Supplier;

public final class Transformed<T, R> extends Delegated<R> {

    public Transformed(
        final Supplier<? extends T> source,
        final Function<? super T, ? extends R> transformation
    ) {
        super(() -> transformation.apply(source.get()));
    }

}

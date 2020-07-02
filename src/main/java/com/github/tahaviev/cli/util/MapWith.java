package com.github.tahaviev.cli.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Map factory.
 *
 * @param <K> key type
 * @param <V> value type
 */
public final class MapWith<K, V> extends Delegated<Map<K, V>> {

    /**
     * Constructor.
     *
     * @param map map factory
     * @param key key
     * @param value value
     */
    public MapWith(
        final Supplier<? extends Map<K, V>> map,
        final K key,
        final Supplier<? extends V> value
    ) {
        super(
            () -> {
                final var result = map.get();
                result.put(key, value.get());
                return result;
            }
        );
    }

    /**
     * Constructor.
     *
     * @param key key
     * @param value value
     */
    public MapWith(final K key, final Supplier<? extends V> value) {
        this(HashMap::new, key, value);
    }

    /**
     * Constructor.
     *
     * @param key key
     * @param value value
     */
    public MapWith(final K key, final V value) {
        this(key, () -> value);
    }

    /**
     * Constructor.
     *
     * @param map map factory
     * @param key key
     * @param value value
     */
    public MapWith(
        final Supplier<? extends Map<K, V>> map,
        final K key,
        final V value
    ) {
        this(map, key, () -> value);
    }

}

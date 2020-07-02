package com.github.tahaviev.cli.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

/**
 * JAXB object from input stream.
 *
 * @param <T> result type
 */
public final class JAXBUnmarshalledFromInput<T> extends Delegated<T> {

    /**
     * Constructor.
     *
     * @param input input stream
     * @param clazz object type
     */
    public JAXBUnmarshalledFromInput(
        final Supplier<? extends InputStream> input,
        final Class<? extends T> clazz
    ) {
        super(
            () -> {
                try {
                    try (var stream = input.get()) {
                        return JAXBContext
                            .newInstance(clazz)
                            .createUnmarshaller()
                            .unmarshal(new StreamSource(stream), clazz)
                            .getValue();
                    }
                } catch (final JAXBException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        );
    }

    /**
     * JAXB object from text.
     *
     * @param <T> result type
     */
    public static final class Text<T> extends Delegated<T> {

        /**
         * Constructor.
         *
         * @param input input text
         * @param clazz object type
         */
        public Text(
            final Supplier<String> input, final Class<? extends T> clazz
        ) {
            super(
                new JAXBUnmarshalledFromInput<>(
                    () -> new ByteArrayInputStream(input.get().getBytes()),
                    clazz
                )
            );
        }

        /**
         * Constructor.
         *
         * @param input input text
         * @param clazz object type
         */
        public Text(final String input, final Class<? extends T> clazz) {
            this(() -> input, clazz);
        }

    }

}

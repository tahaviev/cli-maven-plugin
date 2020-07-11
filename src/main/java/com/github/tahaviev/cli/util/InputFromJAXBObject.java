package com.github.tahaviev.cli.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Supplier;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Input stream from JAXB object.
 *
 * @param <T> JAXB object type
 */
public final class InputFromJAXBObject<T> extends Delegated<InputStream> {

    /**
     * Constructor.
     *
     * @param source JAXB object
     * @param type JAXB object type
     */
    public InputFromJAXBObject(
        final Supplier<? extends T> source,
        final Class<? extends T> type
    ) {
        super(
            () -> {
                final var out = new ByteArrayOutputStream();
                try {
                    JAXBContext.newInstance(type).createMarshaller().marshal(
                        source.get(), out
                    );
                } catch (final JAXBException ex) {
                    throw new RuntimeException(ex);
                }
                return new ByteArrayInputStream(out.toByteArray());
            }
        );
    }

}

package com.github.tahaviev.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 * XSD validation process.
 */
public final class XSDValidation extends RunnableDelegated {

    /**
     * Constructor.
     *
     * @param xml xml input
     * @param xsd xsd
     */
    public XSDValidation(
        final Supplier<? extends InputStream> xml,
        final Supplier<? extends InputStream> xsd
    ) {
        super(
            () -> {
                try (var xmlStream = xml.get(); var xsdStream = xsd.get()) {
                    SchemaFactory
                        .newDefaultInstance()
                        .newSchema(new StreamSource(xsdStream))
                        .newValidator()
                        .validate(new StreamSource(xmlStream));
                } catch (final IOException | SAXException ex) {
                    throw new RuntimeException(ex);
                }
            }
        );
    }

}

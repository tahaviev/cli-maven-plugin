package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.models.xdoc.Td;
import com.github.tahaviev.cli.models.xdoc.Tr;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.function.Supplier;

/**
 * XDoc tr element for table body.
 */
public final class XDocTrForTableBody extends Delegated<Tr> {

    /**
     * Constructor.
     *
     * @param values values
     */
    public XDocTrForTableBody(final Supplier<? extends List<String>> values) {
        super(
            () -> {
                final var result = new Tr();
                for (final var value : values.get()) {
                    final var td = new Td();
                    td.getContent().add(value);
                    result.getThOrTd().add(td);
                }
                return result;
            }
        );
    }

    /**
     * Constructor.
     *
     * @param values values
     */
    public XDocTrForTableBody(final String... values) {
        this(() -> List.of(values));
    }

}

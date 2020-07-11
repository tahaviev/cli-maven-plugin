package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.models.xdoc.Th;
import com.github.tahaviev.cli.models.xdoc.Tr;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.function.Supplier;

/**
 * XDoc tr element for table header.
 */
public final class XDocTrForTableHeader extends Delegated<Tr> {

    /**
     * Constructor.
     *
     * @param names names
     */
    public XDocTrForTableHeader(
        final Supplier<? extends List<String>> names
    ) {
        super(
            () -> {
                final var result = new Tr();
                for (final var name : names.get()) {
                    final var th = new Th();
                    th.getContent().add(name);
                    result.getThOrTd().add(th);
                }
                return result;
            }
        );
    }

    /**
     * Constructor.
     *
     * @param names names
     */
    public XDocTrForTableHeader(final String... names) {
        this(() -> List.of(names));
    }

}

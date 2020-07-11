package com.github.tahaviev.cli.report;

import com.github.tahaviev.cli.models.xdoc.Col;
import com.github.tahaviev.cli.models.xdoc.Colgroup;
import com.github.tahaviev.cli.util.Delegated;
import java.util.List;
import java.util.function.Supplier;

/**
 * XDoc colgroup element for table.
 */
public final class XDocColgroupForTable extends Delegated<Colgroup> {

    /**
     * Constructor.
     *
     * @param values col values
     */
    public XDocColgroupForTable(final Supplier<? extends List<Integer>> values) {
        super(
            () -> {
                final var result = new Colgroup();
                for (final var value : values.get()) {
                    final var col = new Col();
                    col.setWidth(value + "%");
                    result.getCol().add(col);
                }
                return result;
            }
        );
    }

    /**
     * Constructor.
     *
     * @param values col values
     */
    public XDocColgroupForTable(final Integer... values) {
        this(() -> List.of(values));
    }

}

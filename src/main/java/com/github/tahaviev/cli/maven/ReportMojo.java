package com.github.tahaviev.cli.maven;

import com.github.tahaviev.cli.models.xdoc.Document;
import com.github.tahaviev.cli.report.XDocForCommandLine;
import com.github.tahaviev.cli.util.InputFromFile;
import com.github.tahaviev.cli.util.InputFromJAXBObject;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;
import org.apache.maven.doxia.module.xdoc.XdocParser;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;

/**
 * Report generation mojo.
 */
@Mojo(
    name = "report",
    defaultPhase = LifecyclePhase.SITE,
    requiresProject = false,
    inheritByDefault = false
)
public final class ReportMojo extends AbstractMavenReport {

    /**
     * Path to CLI descriptor.
     */
    @Parameter(defaultValue = "src/cli/cli.xml")
    private File descriptor;

    @Override
    public void executeReport(final Locale locale) {
        try {
            new XdocParser().parse(
                new InputStreamReader(
                    new InputFromJAXBObject<>(
                        new XDocForCommandLine(
                            new InputFromFile(this.descriptor)
                        ),
                        Document.class
                    ).get()
                ),
                this.getSink()
            );
        } catch (final ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getDescription(final Locale locale) {
        return "Command line reference";
    }

    @Override
    public String getName(final Locale locale) {
        return "CLI";
    }

    @Override
    public String getOutputName() {
        return "cli";
    }

}

package com.github.tahaviev.cli.maven;

import com.github.tahaviev.cli.code.CodeForCommandLine;
import com.github.tahaviev.cli.util.DirectoryNameFromPackage;
import com.github.tahaviev.cli.util.FileResolved;
import com.github.tahaviev.cli.util.FileWithCreatedDirectoriesIfNotExists;
import com.github.tahaviev.cli.util.InputFromFile;
import com.github.tahaviev.cli.util.InputFromString;
import com.github.tahaviev.cli.util.MapWith;
import com.github.tahaviev.cli.util.OutputFromFile;
import com.github.tahaviev.cli.util.StringJoined;
import com.github.tahaviev.cli.util.Writing;
import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Java code generation mojo.
 */
@Mojo(
    name = "code",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresProject = false,
    inheritByDefault = false
)
public final class CodeMojo extends AbstractMojo {

    /**
     * Build identifier (git commit hash for example).
     */
    @Parameter
    private String build;

    /**
     * Class name for generated code.
     */
    @Parameter(defaultValue = "CommandLine")
    private String className;

    /**
     * Path to CLI descriptor.
     */
    @Parameter(defaultValue = "src/cli/cli.xml")
    private File descriptor;

    /**
     * Path to generated classes output.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/cli")
    private File output;

    /**
     * Package name for generated classes.
     */
    @Parameter(required = true)
    private String packageName;

    /**
     * Maven project.
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * Project version.
     */
    @Parameter(defaultValue = "${project.version}")
    private String version;

    @Override
    public void execute() {
        new Writing(
            new InputFromString(
                new CodeForCommandLine(
                    new InputFromFile(this.descriptor),
                    new MapWith<>(
                        new MapWith<>(
                            new MapWith<>(
                                new MapWith<>("build", this.build),
                                "class",
                                this.className
                            ),
                            "package",
                            this.packageName
                        ),
                        "version",
                        this.version
                    )
                )
            ),
            new OutputFromFile(
                new FileWithCreatedDirectoriesIfNotExists(
                    new FileResolved(
                        new FileResolved(
                            this.output,
                            new DirectoryNameFromPackage(this.packageName)
                        ),
                        new StringJoined(this.className, ".java")
                    )
                )
            )
        ).run();
        this.project.addCompileSourceRoot(this.output.toString());
    }

}

# CLI Maven Plugin
[![](https://github.com/tahaviev/cli-maven-plugin/workflows/ci/badge.svg)](https://github.com/tahaviev/cli-maven-plugin/actions)
[![](https://api.bintray.com/packages/tahaviev/maven/cli-maven-plugin/images/download.svg)](https://bintray.com/tahaviev/maven/cli-maven-plugin/_latestVersion)
[![](https://img.shields.io/github/tag/tahaviev/cli-maven-plugin.svg?label=docs)](https://tahaviev.github.io/cli-maven-plugin/plugin-info.html)
[![](https://img.shields.io/github/tag/tahaviev/cli-maven-plugin.svg?label=changelog)](https://tahaviev.github.io/cli-maven-plugin/github-report.html)

This plugin reads command line descriptor in xml format and then generates 
command line parser and CLI documentation.

## Usage

Add a descriptor to the `src/cli/cli.xml` according 
[XSD schema](https://github.com/tahaviev/cli-maven-plugin/blob/master/src/main/resources/cli.xsd).  

For example:
```xml
<command name="myApp" description="my app description">
    <options>
        <option name="input" description="my app input" required="true" short="i" type="string"/>
        <option name="output" description="my app output" default="out" short="o" type="string"/>
    </options>
</command>
```

Add the plugin to your `pom.xml`.  

```xml
<project>
    <pluginRepositories>
        <pluginRepository>
            <id>bintray-tahaviev</id>
            <url>https://dl.bintray.com/tahaviev/maven/</url>
        </pluginRepository>
    </pluginRepositories>
    <build>
        <plugins>
            <plugin>
                <groupId>com.github.tahaviev</groupId>
                <artifactId>cli-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>code</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <packageName>my.app.cli</packageName>
                </configuration>
            </plugin>
        </plugins>    
    </build>
    <reporting>
        <plugins>
            <plugin>
                <groupId>com.github.tahaviev</groupId>
                <artifactId>cli-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </reporting>
</project>
```
Run `mvn generate-sources site`.  
CLI documentation will be accessible via `target/site/cli.html`.  
Command line parser can be used as in the following example:  
```java
import my.app.cli.CommandLine;

public class MyClass {

    public static void main(String... args) {
        var cli = new CommandLine.Fluent.MyApp(args);
        if (cli.helpRequested()) {
            cli.helpPrint();
        }
        System.out.println("input is " + cli.options().input());
    }
}
```

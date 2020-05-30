# Reproducible Plugin for Apache Maven (obsolete)

A simple [Apache Maven](http://maven.apache.org) plugin to provide some tests for the new [reproducible build](https://maven.apache.org/guides/mini/guide-reproducible-builds.html) features of Apache Maven and its related plugins.

This plugin is designed to be run as part of both the `preparationGoals` and `completionGoals` element of the [Maven Release Plugin](https://maven.apache.org/maven-release/maven-release-plugin/).

When applied, any pom in the current maven session will be rewritten to include the new `project.build.outputTimestamp` property that is looked for by supported plugins:

```xml
  <properties>
    <project.build.outputTimestamp>2019-11-09T05:12:36Z</project.build.outputTimestamp>
  </properties>
```

Notice that the value of the `project.build.outputTimestamp` property injected is "now", then **NOT** reproducible: **DO NOT EXPECT THIS PLUGIN TO MAKE YOUR BUILD REPRODUCIBLE**. This plugin was only useful in 2019'Q3 to make early tests.

Nowadays:
1. add explicitely the `project.build.outputTimestamp` property to your reactor parent POM with your value of choice: the value is reproducible as it is simply written in the pom.xml,
2. let maven-release-plugin 3.0.0-M1 (see [MRELEASE-1029](https://issues.apache.org/jira/browse/MRELEASE-1029)) update the value of the timestamp during the release while it's doing other pom.xml updates, to get a `project.build.outputTimestamp` value that meet release expectations while remaining reproducible (simply written in pom.xml)

## Configuration


```xml
    <plugin>
      <groupId>com.theoryinpractise</groupId>
      <artifactId>reproducible-maven-plugin</artifactId>
      <version>1.0.1</version>
    </plugin>

    <plugin>
      <artifactId>maven-release-plugin</artifactId>
      <version>2.5.3</version>
      <configuration>
        <preparationGoals>clean com.theoryinpractise:reproducible-maven-plugin:apply install
  </preparationGoals>
        <completionGoals>com.theoryinpractise:reproducible-maven-plugin:clear</completionGoals>
        <goals>deploy</goals>
      </configuration>
    </plugin>
```
## Changes

* 1.0.1
  * Initial Release

* 2020Q1: mark this test plugin as obsolete, as it does not make build reproducible and is replaced by maven-release-plugin 3.0.0-M1 (see [MRELEASE-1029](https://issues.apache.org/jira/browse/MRELEASE-1029))

# Reproducible Plugin for Apache Maven

A simple [Apache Maven](http://maven.apache.org) plugin to provide support for the new [reproducible build](https://maven.apache.org/guides/mini/guide-reproducible-builds.html) features of Apache Maven and its related plugins.

This plugin is designed to be run as part of both the `preparationGoals` and `completionGoals` element of the [Maven Release Plugin](https://maven.apache.org/maven-release/maven-release-plugin/).

When applied, any pom in the current maven session will be rewritten to include the new `project.build.outputTimestamp` property that is looked for by supported plugins:

```xml
  <properties>
    <project.build.outputTimestamp>2019-11-09T05:12:36Z</project.build.outputTimestamp>
  </properties>
```

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

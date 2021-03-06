package com.theoryinpractise.reproducablebuild;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jdom2.Element;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** Apply the current build timestamp to all session poms */
@Mojo(name = "apply", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ApplyReproducableBuildMojo extends AbstractReproducableBuildMojo {

  public static final ZoneId UTC = ZoneId.of("UTC");
  public static final String EXECUTION_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(ZonedDateTime.now(UTC));

  @Parameter(required = true, readonly = true, property = "session")
  protected MavenSession session;

  @Override
  public void execute() throws MojoExecutionException {

    for (MavenProject project : session.getAllProjects()) {
      withPomPropertiesElement(
          project.getFile(),
          properties -> {
            Element timestamp = properties.getChild(TIMESTAMP, NAMESPACE);
            if (timestamp == null) {
              timestamp = new Element(TIMESTAMP, NAMESPACE);
              properties.addContent(timestamp);
            }
            timestamp.setText(EXECUTION_TIMESTAMP);
          });
    }
  }
}

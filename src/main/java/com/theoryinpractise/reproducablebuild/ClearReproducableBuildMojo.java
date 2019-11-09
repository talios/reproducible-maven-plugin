package com.theoryinpractise.reproducablebuild;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jdom2.Element;

/** Clear the current build timestamp to all session poms */
@Mojo(name = "clear", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ClearReproducableBuildMojo extends AbstractReproducableBuildMojo {

  @Parameter(required = true, readonly = true, property = "session")
  protected MavenSession session;

  @Override
  public void execute() throws MojoExecutionException {

    for (MavenProject project : session.getAllProjects()) {
      withPomPropertiesElement(
          project.getFile(),
          properties -> {
            Element timestamp = properties.getChild(TIMESTAMP, NAMESPACE);
            if (timestamp != null) {
              timestamp.detach();
            }
          });
    }
  }
}

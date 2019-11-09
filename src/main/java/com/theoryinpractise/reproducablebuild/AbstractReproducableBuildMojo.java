package com.theoryinpractise.reproducablebuild;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.LineSeparator;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedWriter;

public abstract class AbstractReproducableBuildMojo extends AbstractMojo {

  public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");
  public static final String PROPERTIES = "properties";

  public static final String TIMESTAMP = "project.build.outputTimestamp";
  public static final Format FORMAT = Format.getPrettyFormat().setLineSeparator(LineSeparator.SYSTEM).setTextMode(Format.TextMode.PRESERVE);

  public void withPomPropertiesElement(File file, Consumer<Element> consumer) throws MojoExecutionException {
    Document pomDocument;
    try {
      pomDocument = new SAXBuilder().build(file);
      Element projectElem = pomDocument.getRootElement();
      Element properties = projectElem.getChild(PROPERTIES, NAMESPACE);
      if (properties == null) {
        properties = new Element(PROPERTIES, NAMESPACE);
        projectElem.addContent(properties);
      }
      consumer.accept(properties);

      File newPomFile = new File(file.getAbsolutePath());
      File originalPomFile = new File(file.getAbsolutePath() + ".orig");
      if (originalPomFile.exists()) {
        originalPomFile.delete();
      }
      file.renameTo(originalPomFile);

      new XMLOutputter(FORMAT).output(pomDocument, newBufferedWriter(newPomFile.toPath(), UTF_8));

    } catch (IOException | JDOMException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}

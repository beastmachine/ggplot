package org.beastmachine.ggplot.geom.point;

import java.awt.geom.Ellipse2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Circle extends Ellipse2D.Double {

  public Circle(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    super(x-0.5*sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        y-0.5*sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint);
  }
}

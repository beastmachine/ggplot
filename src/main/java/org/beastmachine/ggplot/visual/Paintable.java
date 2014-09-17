package org.beastmachine.ggplot.visual;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

public interface Paintable {
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points);
}

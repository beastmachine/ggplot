package org.beastmachine.ggplot.core;

import java.awt.Dimension;
import java.awt.Graphics2D;

public abstract class GgGeom {
  protected GgAesthetics myMapping;
  
  public abstract void paint2D(Graphics2D g, Dimension pixels, Dimension points);
}

package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

public abstract class Geom {
  protected Aes myMapping;
  
  public abstract void paint2D(Graphics2D g, Dimension pixels, Dimension points);
}

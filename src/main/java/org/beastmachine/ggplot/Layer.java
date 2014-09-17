package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.beastmachine.dataframe.DataFrame;

public class Layer {
  private DataFrame myData;
  private Aes myAes;
  private Stat myStat;
  private Geom myGeom;
  
  public void paint2D(Graphics2D g, Dimension pixels, Dimension points) {
    // myGeom.paint2D(g, pixels, points);
  }
}

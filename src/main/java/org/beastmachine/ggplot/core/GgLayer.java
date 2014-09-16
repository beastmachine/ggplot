package org.beastmachine.ggplot.core;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.beastmachine.dataframe.DataFrame;

public class GgLayer {
  private DataFrame myData;
  private GgAesthetics myAes;
  private GgStat myStat;
  private GgGeom myGeom;
  
  public void paint2D(Graphics2D g, Dimension pixels, Dimension points) {
    // myGeom.paint2D(g, pixels, points);
  }
}

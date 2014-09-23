package org.beastmachine.util;

import java.awt.geom.Dimension2D;

public class GDimension2D extends Dimension2D {

  private double width;
  private double height;
  
  public GDimension2D(double width, double height) {
    this.width = width;
    this.height = height;
  }
  
  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public void setSize(double width, double height) {
    this.width = width;
    this.height = height;
  }

}

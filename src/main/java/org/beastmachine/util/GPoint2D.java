package org.beastmachine.util;

import java.awt.geom.Point2D;

public class GPoint2D extends Point2D {

  private double x;
  private double y;
  
  public GPoint2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public void setLocation(double x, double y) {
    this.x = x;
    this.y = y;
  }
}

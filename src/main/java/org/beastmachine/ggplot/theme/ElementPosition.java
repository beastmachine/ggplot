package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public class ElementPosition {
  
  public static final ElementPosition left =
      new ElementPosition(-2,0, false);
  public static final ElementPosition right =
      new ElementPosition(2,0, false);
  public static final ElementPosition bottom =
      new ElementPosition(0,-2, false);
  public static final ElementPosition top =
      new ElementPosition(0,2, false);
  
  private final double x;
  private final double y;

  public ElementPosition(double x, double y) {
    this(x,y,true);
  }
  
  private ElementPosition(double x, double y, boolean check) {
    if (check) {
      Preconditions.checkArgument(x >=0 && x <= 1,
          "Custom position x must be in range [0, 1]");
      Preconditions.checkArgument(y >=0 && y <= 1,
          "Custom position y must be in range [0, 1]");
    }
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}

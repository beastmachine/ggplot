package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public class Position {
  
  public static final Position left =
      new Position(-2,0, false);
  public static final Position right =
      new Position(2,0, false);
  public static final Position bottom =
      new Position(0,-2, false);
  public static final Position top =
      new Position(0,2, false);
  
  private final double x;
  private final double y;

  public Position(double x, double y) {
    this(x,y,true);
  }
  
  private Position(double x, double y, boolean check) {
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

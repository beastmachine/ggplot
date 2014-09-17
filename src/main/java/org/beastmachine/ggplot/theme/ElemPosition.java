package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public abstract class ElemPosition {

  public static class Custom implements ElementPosition {
    private final double x;
    private final double y;

    public Custom(double x, double y) {
      Preconditions.checkArgument(x >=0 && x <= 1,
          "Custom position x must be in range [0, 1]");
      Preconditions.checkArgument(y >=0 && y <= 1,
          "Custom position y must be in range [0, 1]");
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

  public enum Preset implements ElementPosition {
    left(-2,0),
    right(2,0),
    bottom(0,-2),
    top(0, 2);

    private double x;
    private double y;

    private Preset(double x, double y) {
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
}

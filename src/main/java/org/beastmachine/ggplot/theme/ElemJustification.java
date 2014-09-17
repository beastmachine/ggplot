package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public class ElemJustification {
  
  public static class Custom implements ElementJustification {
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
  
  public enum Preset implements ElementJustification {
    center(0.5,0.5);
    
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

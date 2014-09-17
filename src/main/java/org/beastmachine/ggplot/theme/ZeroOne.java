package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public class ZeroOne {
  public final double val;
  
  public ZeroOne(double val) {
    Preconditions.checkArgument(val >= 0 && val <= 1,
        "ElementZeroOne must be in the range [0, 1]");
    this.val = val;
  }
}

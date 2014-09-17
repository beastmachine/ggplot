package org.beastmachine.ggplot.theme;

import com.google.common.base.Preconditions;

public class ElementZeroOne {
  public final double val;
  
  public ElementZeroOne(double val) {
    Preconditions.checkArgument(val >= 0 && val <= 1,
        "ElementZeroOne must be in the range [0, 1]");
    this.val = val;
  }
}

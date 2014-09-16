package org.beastmachine.ggplot.core;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

public class GgAesthetics {
  private Map<Aesthetic, String> mapping;

  public GgAesthetics() {
    mapping = new TreeMap<Aesthetic, String>();
  }

  public GgAesthetics a(Aesthetic key, String val) {
    mapping.put(key, val);
    return this;
  }

  public String getVariable(Aesthetic aes) {
    Preconditions.checkNotNull(aes);
    String variable = mapping.get(aes);
    Preconditions.checkNotNull(variable,
        "GgAesthetics object does not have value for "+aes.name());
    return variable;
  }

  public enum Aesthetic {
    adj, alpha, angle, bg, cex, col, color, colour, fg, fill, group,
    hjust, label, linetype, lower, lty, lwd, max, middle, min, order,
    pch, radius, sample, shape, size, srt, upper, vjust, weight, width,
    x, xend, xmax, xmin, xintercept, y, yend, ymax, ymin, yintercept, z;
  }
}

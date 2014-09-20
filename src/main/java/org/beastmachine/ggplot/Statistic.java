package org.beastmachine.ggplot;

import org.beastmachine.dataframe.Column;

public abstract class Statistic {
  public abstract Column apply(Column c);
}

package org.beastmachine.ggplot;

import org.beastmachine.dataframe.Column;

public abstract class Stat {
  public abstract Column apply(Column c);
}

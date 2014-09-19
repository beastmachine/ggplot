package org.beastmachine.ggplot;

import org.beastmachine.dataframe.Column;

public class StatIdentity extends Statistic {

  @Override
  public Column apply(Column c) {
    return c;
  }
}

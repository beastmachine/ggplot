package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;

public class StatIdentity extends Statistic {

  @Override
  public Column apply(Column c) {
    return c;
  }
}
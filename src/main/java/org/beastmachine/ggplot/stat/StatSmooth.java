package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;

import com.google.common.base.Preconditions;

public class StatSmooth extends Statistic {

  @Override
  public Column apply(Column c) {
    Preconditions.checkState(false, "stat_smooth not yet supported");
    return null;
  }

}

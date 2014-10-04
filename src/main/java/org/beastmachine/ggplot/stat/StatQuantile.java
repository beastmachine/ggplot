package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;

import com.google.common.base.Preconditions;

public class StatQuantile extends Statistic {

  @Override
  public Column apply(Column c) {
    Preconditions.checkState(false, "stat_quantile not yet supported");
    return null;
  }

}

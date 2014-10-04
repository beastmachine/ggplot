package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;

import com.google.common.base.Preconditions;

import org.beastmachine.ggplot.GGPlot;
import org.beastmachine.ggplot.Layer;
import org.beastmachine.ggplot.geom.path.GeometryBar;

public class StatBin extends Statistic implements Layer.OptionSetter, GeometryBar.OptionSetter {

  @Override
  public Column apply(Column c) {
    Preconditions.checkState(false,"stat_bin not currently supported");
    return null;
  }

}

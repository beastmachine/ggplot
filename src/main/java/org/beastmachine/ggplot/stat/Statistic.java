package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;
import org.beastmachine.ggplot.GGPlot;
import org.beastmachine.ggplot.Layer;

public abstract class Statistic implements Layer.OptionSetter {
  public abstract Column apply(Column c);
  
  @Override
  public void setOptions(Layer layer) {
    layer.setStat(this);
  }

  @Override
  public void setOptions(GGPlot plot) {
    plot.setStat(this);
  }
}

package org.beastmachine.ggplot.stat;

import org.beastmachine.dataframe.Column;
import org.beastmachine.ggplot.GGPlot;
import org.beastmachine.ggplot.Layer;

public class StatIdentity extends Statistic implements Layer.OptionSetter{

  @Override
  public Column apply(Column c) {
    return c;
  }


  
}

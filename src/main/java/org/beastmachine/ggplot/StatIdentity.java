package org.beastmachine.ggplot;

import org.beastmachine.dataframe.Column;

public class StatIdentity extends Stat {

  @Override
  public Column apply(Column c) {
    return c;
  }
}

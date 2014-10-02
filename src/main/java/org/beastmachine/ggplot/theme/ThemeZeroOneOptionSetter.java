package org.beastmachine.ggplot.theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeZeroOneOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyZeroOne> myTargets;
  private ZeroOne unit;
  
  public ThemeZeroOneOptionSetter(Theme.KeyZeroOne ... targets) {
    myTargets = new ArrayList<Theme.KeyZeroOne>();
    for (Theme.KeyZeroOne t : targets) {
      myTargets.add(t);
    }
    unit = null;
  }

  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyZeroOne key : myTargets) {
      if (unit != null) { theme.put(key, unit); }
    }
  }
  
  public ThemeZeroOneOptionSetter unit(double x) {
    this.unit = new ZeroOne(x);
    return this;
  }
}

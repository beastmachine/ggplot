package org.beastmachine.ggplot.theme;

import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.theme.Unit.UnitType;

public class ThemeUnitOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyUnit> myTargets;
  private Unit unit;
  
  public ThemeUnitOptionSetter(Theme.KeyUnit ... targets) {
    myTargets = new ArrayList<Theme.KeyUnit>();
    for (Theme.KeyUnit t : targets) {
      myTargets.add(t);
    }
    unit = null;
  }

  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyUnit key : myTargets) {
      if (unit != null) { theme.put(key, unit); }
    }
  }
  
  public ThemeUnitOptionSetter unit(UnitType unit, double ... x) {
    this.unit = new Unit(unit, x);
    return this;
  }
}

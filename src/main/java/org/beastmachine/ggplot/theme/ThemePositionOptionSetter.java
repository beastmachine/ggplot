package org.beastmachine.ggplot.theme;

import java.util.ArrayList;
import java.util.List;

public class ThemePositionOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyPosition> myTargets;
  private Position pos;
  
  public ThemePositionOptionSetter(Theme.KeyPosition ... targets) {
    myTargets = new ArrayList<Theme.KeyPosition>();
    for (Theme.KeyPosition t : targets) {
      myTargets.add(t);
    }
    pos = null;
  }
  
  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyPosition key : myTargets) {
      if (pos != null) { theme.put(key, pos); }
    }
  }
  
  public ThemePositionOptionSetter position(Position position) {
    this.pos = position;
    return this;
  }

}

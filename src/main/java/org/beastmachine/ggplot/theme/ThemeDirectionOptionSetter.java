package org.beastmachine.ggplot.theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeDirectionOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyDirection> myTargets;
  private Direction direction;
  
  public ThemeDirectionOptionSetter(Theme.KeyDirection ... targets) {
    myTargets = new ArrayList<Theme.KeyDirection>();
    for (Theme.KeyDirection t : targets) {
      myTargets.add(t);
    }
    direction = null;
  }
  
  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyDirection key : myTargets) {
      if (direction != null) { theme.put(key, direction); }
    }
  }
  
  public ThemeDirectionOptionSetter position(Direction direction) {
    this.direction = direction;
    return this;
  }

}

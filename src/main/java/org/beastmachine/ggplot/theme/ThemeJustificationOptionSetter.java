package org.beastmachine.ggplot.theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeJustificationOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyJustification> myTargets;
  private Justification just;
  
  public ThemeJustificationOptionSetter(Theme.KeyJustification ... targets) {
    myTargets = new ArrayList<Theme.KeyJustification>();
    for (Theme.KeyJustification t : targets) {
      myTargets.add(t);
    }
    just = null;
  }
  
  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyJustification key : myTargets) {
      if (just != null) { theme.put(key, just); }
    }
  }
  
  public ThemeJustificationOptionSetter justification(Justification just) {
    this.just = just;
    return this;
  }

}

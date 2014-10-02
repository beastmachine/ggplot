package org.beastmachine.ggplot.theme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.theme.Line.LineType;
import org.beastmachine.ggplot.visual.Colors;

public class ThemeRectOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyRect> myTargets;
  private Color fill;
  private Color color;
  private double size;
  private LineType lineType;

  public ThemeRectOptionSetter(Theme.KeyRect ... targets) {
    myTargets = new ArrayList<Theme.KeyRect>();
    for (Theme.KeyRect t : targets) {
      myTargets.add(t);
    }
    fill = null;
    color = null;
    size = Double.NaN;
    lineType = null;
  }

  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyRect key : myTargets) {
      Rect target = theme.get(key);
      if (fill != null)        { target.setFill(fill); }
      if (color != null)       { target.setColor(color); }
      if (!Double.isNaN(size)) { target.setSize(size); }
      if (lineType != null)    { target.setLineType(lineType); }
    }
  }

  public ThemeRectOptionSetter fill(Color fill) {
    this.fill = fill;
    return this;
  }

  public ThemeRectOptionSetter color(Color color) {
    this.color = color;
    return this;
  }

  public ThemeRectOptionSetter size(double size) {
    this.size = size;
    return this;
  }

  public ThemeRectOptionSetter lineType(LineType lineType) {
    this.lineType = lineType;
    return this;
  }

  public ThemeRectOptionSetter blank() {
    this.color = Colors.invisible;
    this.fill = Colors.invisible;
    this.size = 0;
    this.lineType = LineType.blank;
    return this;
  }
  
}

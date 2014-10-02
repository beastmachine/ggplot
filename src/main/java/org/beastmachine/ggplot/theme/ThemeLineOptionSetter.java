package org.beastmachine.ggplot.theme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.theme.Line.LineEnd;
import org.beastmachine.ggplot.theme.Line.LineType;
import org.beastmachine.ggplot.visual.Colors;

public class ThemeLineOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyLine> myTargets;
  private Color color;
  private double size;
  private LineType lineType;
  private LineEnd lineEnd;

  public ThemeLineOptionSetter(Theme.KeyLine ... targets) {
    myTargets = new ArrayList<Theme.KeyLine>();
    for (Theme.KeyLine t : targets) {
      myTargets.add(t);
    }
    color = null;
    size = Double.NaN;
    lineType = null;
    lineEnd = null;
  }

  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyLine key : myTargets) {
      Line target = theme.get(key);
      if (color != null)       { target.setColor(color); }
      if (!Double.isNaN(size)) { target.setSize(size); }
      if (lineType != null)    { target.setLineType(lineType); }
      if (lineEnd != null)     { target.setLineEnd(lineEnd); }
    }
  }

  public ThemeLineOptionSetter color(Color color) {
    this.color = color;
    return this;
  }  
  
  public ThemeLineOptionSetter size(double size) {
    this.size = size;
    return this;
  }  
  
  public ThemeLineOptionSetter lineType(LineType lineType) {
    this.lineType = lineType;
    return this;
  }  
  
  public ThemeLineOptionSetter lineEnd(LineEnd lineEnd) {
    this.lineEnd = lineEnd;
    return this;
  }
  
  public ThemeLineOptionSetter blank() {
    this.color = Colors.invisible;
    this.size = 0;
    return this;
  }
}
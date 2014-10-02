package org.beastmachine.ggplot.theme;

import static java.lang.Double.isNaN;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.theme.Text.Face;
import org.beastmachine.ggplot.visual.Colors;

public class ThemeTextOptionSetter implements ThemeOptionSetter {

  private List<Theme.KeyText> myTargets;
  private String family;
  private Face face;
  private Color color;
  private double size;
  private ZeroOne hjust;
  private ZeroOne vjust;
  private double angle;
  private double lineheight;

  public ThemeTextOptionSetter(Theme.KeyText ... targets) {
    myTargets = new ArrayList<Theme.KeyText>();
    for (Theme.KeyText t : targets) {
      myTargets.add(t);
    }
    family = null;
    face = null;
    color = null;
    size = Double.NaN;
    hjust = null;
    vjust = null;
    angle = Double.NaN;
    lineheight = Double.NaN;
  }

  @Override
  public void setOptions(Theme theme) {
    for (Theme.KeyText key : myTargets) {
      Text target = theme.get(key);
      if (family != null)     { target.setFamily(family); }
      if (face != null)       { target.setFace(face); }
      if (color != null)      { target.setColor(color); }
      if (!isNaN(size))       { target.setSize(size); }
      if (hjust != null)      { target.setHjust(hjust); }
      if (vjust != null)      { target.setVjust(vjust); }
      if (!isNaN(angle))      { target.setAngle(angle); }
      if (!isNaN(lineheight)) { target.setLineheight(lineheight); }
    }
  }

  public ThemeTextOptionSetter family(String family) {
    this.family = family;
    return this;
  }

  public ThemeTextOptionSetter face(Face face) {
    this.face = face;
    return this;
  }

  public ThemeTextOptionSetter color(Color color) {
    this.color = color;
    return this;
  }

  public ThemeTextOptionSetter size(double size) {
    this.size = size;
    return this;
  }

  public ThemeTextOptionSetter hjust(ZeroOne hjust) {
    this.hjust = hjust;
    return this;
  }

  public ThemeTextOptionSetter vjust(ZeroOne vjust) {
    this.vjust = vjust;
    return this;
  }

  public ThemeTextOptionSetter angle(double angle) {
    this.angle = angle;
    return this;
  }

  public ThemeTextOptionSetter lineheight(double lineheight) {
    this.lineheight = lineheight;
    return this;
  }
  
  public ThemeTextOptionSetter blank() {
    this.color = Colors.invisible;
    this.size = 0;
    return this;
  }

}

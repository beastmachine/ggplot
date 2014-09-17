package org.beastmachine.ggplot.theme;

import static java.lang.Math.round;

import java.awt.Color;
import java.awt.Font;

public class Text {
  private String family;
  private Face face;
  private Color color;
  private double size;
  private ZeroOne hjust;
  private ZeroOne vjust;
  private double angle;
  private double lineheight;
  
  public Text(String family, Face face, Color color, double size,
      ZeroOne hjust, ZeroOne vjust, double angle,
      double lineheight) {
    this.family = family;
    this.face = face;
    this.color = color;
    this.size = size;
    this.hjust = hjust;
    this.vjust = vjust;
    this.angle = angle;
    this.lineheight = lineheight;
  }
  
  public Font getFont(double pixelsPerPoint) {
    return new Font(family, face.fontStyle,
        (int)round(size*pixelsPerPoint));
  }
  
  public Color getColor() {
    return color;
  }
  
  public double getAngle() {
    return angle;
  }
  
  public double getLineHeight(double pixelsPerPoint) {
    return lineheight*pixelsPerPoint;
  }
  
  public double getVerticalJustification() {
    return vjust.val;
  }
  
  public double getHorizontalJustification() {
    return hjust.val;
  }
  
  public enum Face {
    bold(Font.BOLD),
    italic(Font.ITALIC),
    plain(Font.PLAIN);
    
    public final int fontStyle;
    
    private Face(int fontStyle) {
      this.fontStyle = fontStyle;
    }
  }
}

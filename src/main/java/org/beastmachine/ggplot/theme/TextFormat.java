package org.beastmachine.ggplot.theme;

import static java.lang.Math.round;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextFormat {
  private String family;
  private Face face;
  private Color color;
  private double size;
  private ZeroOne hjust;
  private ZeroOne vjust;
  private double angle;
  private double lineheight;
  
  public TextFormat() { }
  
  public TextFormat(String family, Face face, Color color, double size,
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
  
  public TextFormat(TextFormat other) {
    this(other.family, other.face, other.color, other.size, other.hjust,
        other.vjust, other.angle, other.lineheight);
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public void setFace(Face face) {
    this.face = face;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setSize(double size) {
    this.size = size;
  }

  public void setHjust(ZeroOne hjust) {
    this.hjust = hjust;
  }

  public void setVjust(ZeroOne vjust) {
    this.vjust = vjust;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public void setLineheight(double lineheight) {
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
  
  public double getLineHeight(Graphics2D g, double pixelsPerPoint){
    FontMetrics fm = g.getFontMetrics(this.getFont(pixelsPerPoint));
    return fm.getHeight();
  }
  
  public double getLineWidth(Graphics2D g, String s, double pixelsPerPoint){
    FontMetrics fm = g.getFontMetrics(this.getFont(pixelsPerPoint));
    return fm.stringWidth(s);
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

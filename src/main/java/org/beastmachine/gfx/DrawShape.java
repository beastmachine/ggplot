package org.beastmachine.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;

public class DrawShape implements Paintable {
  private Color fillColor;
  private Shape fillShape;
  private Stroke drawStroke;
  private Color drawColor;
  private Shape drawShape;
  
  public DrawShape(Shape fillShape, Shape drawShape, Color fillColor,
      Color drawColor, Stroke drawStroke) {
    this.fillShape = fillShape;
    this.drawShape = drawShape;
    this.fillColor = fillColor;
    this.drawColor = drawColor;
    this.drawStroke = drawStroke;
  }
  
  public DrawShape(Shape shape, Color fillColor, Color drawColor,
      Stroke drawStroke) {
    this(shape, shape, fillColor, drawColor, drawStroke);
  }
  
  @Override
  public void paint(Graphics2D g) {
    g.setColor(fillColor);
    g.fill(fillShape);
    g.setStroke(drawStroke);
    g.setColor(drawColor);
    g.draw(drawShape);
  }
  
  public boolean contains(Point p) {
    return fillShape.contains(p) || drawShape.contains(p);
  }
}

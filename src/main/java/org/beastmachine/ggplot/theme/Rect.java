package org.beastmachine.ggplot.theme;

import static org.beastmachine.ggplot.visual.GeomConstants.POINTS_PER_075_MM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class Rect {
  private Color fill;
  private Color color;
  private double size;
  private Line.LineType lineType;
  
  public Rect(Color fill, Color color, double size,
      Line.LineType lineType) {
    this.fill = fill;
    this.color = color;
    this.size = size;
    this.lineType = lineType;
  }
  
  public Stroke getStroke(double pixelsPerPoint) {
    return new BasicStroke(
        (float)(size*POINTS_PER_075_MM*pixelsPerPoint),
        BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND,
        10f, lineType.getDash(), 0f);
  }
  
  public Color getFill() {
    return fill;
  }
  
  public Color getColor() {
    return color;
  }
}

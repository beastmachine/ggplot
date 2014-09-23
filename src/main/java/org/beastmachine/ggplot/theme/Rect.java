package org.beastmachine.ggplot.theme;

import static org.beastmachine.ggplot.visual.GeomConstants.POINTS_PER_075_MM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import org.beastmachine.ggplot.visual.Graphics2DState;

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
  
  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }

  public Line.LineType getLineType() {
    return lineType;
  }

  public void setLineType(Line.LineType lineType) {
    this.lineType = lineType;
  }

  public void setFill(Color fill) {
    this.fill = fill;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public BasicStroke getStroke(double pixelsPerPoint) {
    float width = (float)(size*POINTS_PER_075_MM*pixelsPerPoint);
    if (width == 0) { width = 1; }
    float[] dash = lineType.getDash();
    float[] dashCp = Arrays.copyOf(dash, dash.length);
    for (int ii=0; ii<dashCp.length; ii++) {
      dashCp[ii] = (float)(width*dashCp[ii]);
    }
    
    return new BasicStroke(width, BasicStroke.CAP_ROUND,
        BasicStroke.CAP_ROUND, 10f, dashCp, 0f);
  }
  
  public Color getFill() {
    return fill;
  }
  
  public Color getColor() {
    return color;
  }

  public void paint2D(Graphics2D g,
      Rectangle2D pointBounds,
      double pixelsPerPoint) {
    Graphics2DState state = new Graphics2DState(g);

    BasicStroke stroke = getStroke(pixelsPerPoint);
    double lineWidth = stroke.getLineWidth();

    Rectangle2D pixelBounds =
        new Rectangle2D.Double(
            pointBounds.getX()*pixelsPerPoint,
            pointBounds.getY()*pixelsPerPoint,
            pointBounds.getWidth()*pixelsPerPoint,
            pointBounds.getHeight()*pixelsPerPoint);
    if (getColor().getAlpha() > 0.0) {
      pixelBounds.setRect(
          pixelBounds.getX() + lineWidth/2,
          pixelBounds.getY() + lineWidth/2,
          pixelBounds.getWidth() - lineWidth,
          pixelBounds.getHeight() - lineWidth);
    }

//    System.out.println("Ready to paint:");
//    System.out.println("x: "+pixelBounds.getX());
//    System.out.println("y: "+pixelBounds.getY());
//    System.out.println("w: "+pixelBounds.getWidth());
//    System.out.println("h: "+pixelBounds.getHeight());
    
    g.setColor(getFill());
    g.fill(pixelBounds);
    g.setColor(getColor());
    g.setStroke(stroke);
    g.draw(pixelBounds);

    state.restore(g);
  }

}

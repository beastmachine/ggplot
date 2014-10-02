package org.beastmachine.ggplot.geom.point;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.ggplot.visual.Paintable;

public class QwikDraw implements Paintable {


  private double[] x;
  private double[] y;
  private int shape;
  private double size;

  private double xMin;
  private double xMax;
  private double yMin;
  private double yMax;

  public QwikDraw(double[] x, double[] y, int shape, double size) {
    this.x = x;
    this.y = y;
    this.shape = shape;
    this.size = size;
    init();
  }

  private void init() {
    xMin = Double.POSITIVE_INFINITY;
    xMax = Double.NEGATIVE_INFINITY;
    yMin = Double.POSITIVE_INFINITY;
    yMax = Double.NEGATIVE_INFINITY;
    for (double xi : x) {
      if (xi < xMin) {
        xMin = xi;
      }
      if (xi > xMax) {
        xMax = xi;
      }
    }
    for (double yi : y) {
      if (yi < yMin) {
        yMin = yi;
      }
      if (yi > yMax) {
        yMax = yi;
      }
    }
    double xMid = (xMin+xMax)/2;
    double yMid = (yMin+yMax)/2;
    double xHalfRange = xMax-xMid;
    double yHalfRange = yMax-yMid;
    xHalfRange *= 1.1;
    yHalfRange *= 1.1;
    xMin = xMid-xHalfRange;
    xMax = xMid+xHalfRange;
    yMin = yMid-yHalfRange;
    yMax = yMid+yHalfRange;
  }

  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
    Graphics2DState state = new Graphics2DState(g);

    double pixelsPerPoint = pixels.getWidth()/points.getWidth();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke((float)(0.75*pixelsPerPoint),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    Shape shape = null;
    switch (this.shape) {
    case 0:
      shape = new Square(0, 0, this.size, pixelsPerPoint);
      break;
    case 1:
      shape = new Circle(0, 0, this.size,pixelsPerPoint);
      break;
    case 2:
      shape = new Triangle(0, 0, this.size, pixelsPerPoint);
      break;
    case 3:
      shape = new Cross(0, 0, this.size, pixelsPerPoint);
      break;
    case 4:
      shape = new Ex(0, 0,this.size,pixelsPerPoint);
      break;
    case 5:
      shape = new Diamond(0, 0, this.size, pixelsPerPoint);
      break;
    case 6:
      shape = new DownTriangle(0, 0, this.size, pixelsPerPoint);
      break;
    case 7:
      shape = new ExBox(0, 0, this.size, pixelsPerPoint);
      break;
    case 8:
      shape = new Asterisk(0, 0, this.size, pixelsPerPoint);
      break;
    case 9:
      shape = new CrossDiamond(0, 0, this.size, pixelsPerPoint);
      break;
    case 10:
      shape = new CrossCircle(0, 0,this.size, pixelsPerPoint);
      break;
    case 11:
      shape = new Hexagram(0, 0, this.size, pixelsPerPoint);
      break;
    default:
      break;
    }

    if (shape != null) {
      for (int ii=0; ii<x.length; ii++) {
        double xi = x[ii];
        double yi = y[ii];
        double screenX = x2screen(xi, pixels.getWidth());
        double screenY = y2screen(yi,pixels.getHeight());
        if (shape instanceof Ellipse2D.Double) {
          screenX -= shape.getBounds().getWidth()/2;
          screenY -= shape.getBounds().getHeight()/2;
          ((Ellipse2D.Double) shape).x = screenX;
          ((Ellipse2D.Double) shape).y = screenY;
        } else if (shape instanceof Rectangle2D.Double){
          screenX -= shape.getBounds().getWidth()/2;
          screenY -= shape.getBounds().getHeight()/2;
          ((Rectangle2D.Double) shape).x = screenX;
          ((Rectangle2D.Double) shape).y = screenY;
        } else if (shape instanceof Triangle) {
          ((Triangle) shape).x = screenX;
          ((Triangle) shape).y = screenY;
        } else if (shape instanceof Cross) {
          ((Cross) shape).x = screenX;
          ((Cross) shape).y = screenY;
        } else if (shape instanceof Ex) {
          ((Ex) shape).x = screenX;
          ((Ex) shape).y = screenY;
        } else if (shape instanceof Diamond) {
          ((Diamond) shape).x = screenX;
          ((Diamond) shape).y = screenY;
        } else if (shape instanceof DownTriangle) {
          ((DownTriangle) shape).x = screenX;
          ((DownTriangle) shape).y = screenY;
        } else if (shape instanceof Asterisk) {
          ((Asterisk) shape).x = screenX;
          ((Asterisk) shape).y = screenY;
        } else if (shape instanceof CrossDiamond) {
          ((CrossDiamond) shape).x = screenX;
          ((CrossDiamond) shape).y = screenY;
        } else if (shape instanceof CrossCircle) {
          screenX -= shape.getBounds().getWidth()/2;
          screenY -= shape.getBounds().getHeight()/2;
          ((CrossCircle) shape).x = screenX;
          ((CrossCircle) shape).y = screenY;
        } else if (shape instanceof Hexagram) {
          ((Hexagram) shape).x = screenX;
          ((Hexagram) shape).y = screenY;
        }
        g.draw(shape);
      }
    }

    state.restore(g);
  }

  private double x2screen(double xi, double width) {
    double proportion = (xi-xMin)/(xMax-xMin);
    double pixel = width*proportion;
    return pixel;
  }

  private double y2screen(double yi, double height) {
    double proportion = (yi-yMin)/(yMax-yMin);
    double pixel = height*(1-proportion);
    return pixel;
  }

  public static void main(String[] args) throws IOException {
    double[] x = {1,2,3,4,5,6,7,8,9,10};
    double[] y = {2,3,4,5,6,7,8,9,10,11};

    QwikDraw qd = new QwikDraw(x, y, 1, 5);
    PaintPDF.paintToPDF(qd,
        new Dimension(621,480),
        new Dimension(792,612),
        "/Users/Peter/Documents/canI.pdf");
  }

}

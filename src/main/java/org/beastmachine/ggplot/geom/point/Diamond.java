package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.sqrt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Diamond implements Shape {
  private static final double LENGTH_CONSTANT = sqrt(2)/2.0;

  public double x;
  public double y;
  protected double sizeIn075mm;
  protected double pixelsPerPoint;

  protected double radius;

  public Diamond(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;
    this.radius = radius();
  }

  private double radius() {
    return sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*LENGTH_CONSTANT;
  }

  public Rectangle getBounds() {
    return getBounds2D().getBounds();
  }

  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(x-radius, y-radius, 2*radius, 2*radius);
  }

  public boolean contains(double x, double y) {
    if (x > this.x) {
      if (y > this.y){
        return (x-this.x)+(y-this.y) < radius;
      } else {
        return (x-this.x)+(this.y-y) < radius;
      }
    } else {
      if (y > this.y) {
        return (this.x-x)+(y-this.y) < radius;
      }else {
        return (this.x-x)+(this.y-y) < radius;
      }
    }
  }

  public boolean contains(Point2D p) {
    return contains(p.getX(), p.getY());
  }

  public boolean intersects(double x, double y, double w, double h) {
    return intersects(new Rectangle2D.Double(x, y, w, h));
  }

  public boolean intersects(Rectangle2D r) {
    double rX = r.getX();
    double rY = r.getY();
    double rW = r.getWidth();
    double rH = r.getHeight();
    return r.contains(x,y-radius) || r.contains(x,y+radius) ||
        r.contains(x-radius,y) || r.contains(x+radius,y) ||
        contains(rX,rY) || contains(rX,rY+rH) ||
        contains(rX+rW, rY) || contains(rX+rW,rY+rH);
  }

  public boolean contains(double x, double y, double w, double h) {
    return contains(x,y) && contains(x,y+h) &&
        contains(x+w,y) && contains(x+w, y+h);
  }

  public boolean contains(Rectangle2D r) {
    return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
  }

  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt(at);
  }

  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return new PathIt(at);
  }

  private class PathIt extends PathIterators {

    private PathIt(AffineTransform at) {
      super(at);
    }

    public int currentSegment(double[] coords) {
      switch(myState) {
      case 0: return m(x,        y-radius, coords);
      case 1: return e(x+radius, y,        coords);
      case 2: return e(x,        y+radius, coords);
      case 3: return e(x-radius, y,        coords);
      case 4: return e(x,        y-radius, coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(x,        y-radius, coords);
      case 1: return e(x+radius, y,        coords);
      case 2: return e(x,        y+radius, coords);
      case 3: return e(x-radius, y,        coords);
      case 4: return e(x,        y-radius, coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 5;
    }
  }

}

package org.beastmachine.ggplot.geom.point;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Cross implements Shape {

  private static final double LENGTH_CONSTANT = 0.705555555555;

  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;

  private double radius;

  public Cross(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
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
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(Point2D p) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean intersects(double x, double y, double w, double h) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean intersects(Rectangle2D r) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(double x, double y, double w, double h) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean contains(Rectangle2D r) {
    // TODO Auto-generated method stub
    return false;
  }

  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt();
  }

  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return new PathIt();
  }

  private class PathIt extends PathIterators {
    public int currentSegment(double[] coords) {
      switch(myState) {
      case 0: return m(x,        y-radius, coords);
      case 1: return e(x,        y+radius, coords);
      case 2: return m(x-radius, y,        coords);
      case 3: return e(x+radius, y,        coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(x,        y-radius, coords);
      case 1: return e(x,        y+radius, coords);
      case 2: return m(x-radius, y,        coords);
      case 3: return e(x+radius, y,        coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 4;
    }
  }

}

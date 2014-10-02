package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.sqrt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Triangle implements Shape {

  private static final double RADIUS_CONSTANT = 0.776;
  private static final double sqrt3 = sqrt(3);
  
  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;

  private double minX;
  private double maxX;
  private double minY;
  private double maxY;

  public Triangle(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;
    this.minX = x - 0.5*sqrt3*radius();
    this.maxX = x + 0.5*sqrt3*radius();
    this.maxY = y + 0.5*radius();
    this.minY = y - radius();
  }

  private double radius() {
    return sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*RADIUS_CONSTANT;
  }

  private Point2D top() {
    return new Point2D.Double(x, minY);
  }

  private Point2D loLeft() {
    return new Point2D.Double(minX,maxY);
  }
  private Point2D loRite() {
    return new Point2D.Double(maxX, maxY);
  }

  public Rectangle getBounds() {
    return getBounds2D().getBounds();
  }

  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(loLeft().getX(), top().getY(), 
        (loRite().getX()-loLeft().getX()), (top().getY()-loLeft().getY()));
  }

  public boolean contains(double x, double y) {
    return (y <= maxY) &&
        (y >= sqrt3*(x-maxX)+maxY) &&
        (y >= -sqrt3*(x-minX)+maxY);
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
    return r.contains(minX, maxY) || r.contains(maxX, maxY) ||
        r.contains(x, minY) ||
        contains(rX, rY) || contains(rX+rW, rY) ||
        contains(rX, rY+rH) || contains(rX+rW, rY+rH);
  }

  public boolean contains(double x, double y, double w, double h) {
    return contains(x,y) && contains(x+w, y) &&
        contains(x, y+h) && contains(x+w, y+h);
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
      case 0: return m(top().getX(),    top().getY(),    coords);
      case 1: return e(loRite().getX(), loRite().getY(), coords);
      case 2: return e(loLeft().getX(), loLeft().getY(), coords);
      case 3: return e(top().getX(),    top().getY(),    coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(top().getX(),    top().getY(),    coords);
      case 1: return e(loRite().getX(), loRite().getY(), coords);
      case 2: return e(loLeft().getX(), loLeft().getY(), coords);
      case 3: return e(top().getX(),    top().getY(),    coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 4;
    }
  }

}

package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.sqrt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class DownTriangle implements Shape {

  private static final double RADIUS_CONSTANT = 0.776;
  private static final double sqrt3 = sqrt(3);
  
  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;

  private double maxX;
  private double minX;
  private double maxY;
  private double minY;

  public DownTriangle(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;
    this.maxX = x - 0.5*sqrt3*radius();
    this.minX = x + 0.5*sqrt3*radius();
    this.minY = y - 0.5*radius();
    this.maxY = y + radius();
  }

  private double radius() {
    return sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*RADIUS_CONSTANT;
  }

  private Point2D top() {
    return new Point2D.Double(x, maxY);
  }

  private Point2D loLeft() {
    return new Point2D.Double(maxX,minY);
  }
  private Point2D loRite() {
    return new Point2D.Double(minX, minY);
  }

  public Rectangle getBounds() {
    return getBounds2D().getBounds();
  }

  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(loLeft().getX(), top().getY(), 
        (loRite().getX()-loLeft().getX()), (top().getY()-loLeft().getY()));
  }

  public boolean contains(double x, double y) {
    return (y >= minY) &&
        (y <= sqrt3*(minX-x)+minY) &&
        (y <= -sqrt3*(maxX-x)+minY);
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
    return r.contains(maxX, minY) || r.contains(minX, minY) ||
        r.contains(x, maxY) ||
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

package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Hexagram implements Shape {

  private static final double TOP_BOTTOM_CONSTANT = 0.776;
  private static final double DX_CONSTANT = 0.672624647;
  private static final double DY_CONSTANT = 0.582831609;

  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;

  private double bigDy;
  private double dx;
  private double dy;

  public Hexagram(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;

    bigDy = sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*TOP_BOTTOM_CONSTANT;
    dx = sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*DX_CONSTANT;
    dy = sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*DY_CONSTANT;
  }

  public Rectangle getBounds() {
    return getBounds2D().getBounds();
  }

  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(x-dx,y-bigDy,2*dx, 2*bigDy);
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
      case 0: return m(x,    y-bigDy, coords);
      case 1: return e(x+dx, y+dy,    coords);
      case 2: return e(x-dx, y+dy,    coords);
      case 3: return e(x,    y-bigDy, coords);
      case 4: return m(x,    y+bigDy, coords);
      case 5: return e(x-dx, y-dy,    coords);
      case 6: return e(x+dx, y-dy,    coords);
      case 7: return e(x,    y+bigDy, coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(x,    y-bigDy, coords);
      case 1: return e(x+dx, y+dy,    coords);
      case 2: return e(x-dx, y+dy,    coords);
      case 3: return e(x,    y-bigDy, coords);
      case 4: return m(x,    y+bigDy, coords);
      case 5: return e(x-dx, y-dy,    coords);
      case 6: return e(x+dx, y-dy,    coords);
      case 7: return e(x,    y+bigDy, coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 8;
    }
  }

}

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

public class DownTriangle implements Shape {

  private static final double RADIUS_CONSTANT = 0.776;

  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;


  public DownTriangle(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;
  }

  private double radius() {
    return sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*RADIUS_CONSTANT;
  }

  private Point2D bottom() {
    return new Point2D.Double(x, y+radius());
  }

  private Point2D hiLeft() {
    return new Point2D.Double(x+radius()*sin(-2*PI/3),y+radius()*cos(-2*PI/3));
  }
  private Point2D hiRite() {
    return new Point2D.Double(x+radius()*sin(2*PI/3), y+radius()*cos(2*PI/3));
  }

  public Rectangle getBounds() {
    return getBounds2D().getBounds();
  }

  public Rectangle2D getBounds2D() {
    return new Rectangle2D.Double(hiLeft().getX(), hiLeft().getY(), 
        (hiRite().getX()-hiLeft().getX()), (hiLeft().getY()-bottom().getY()));
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
      case 0: return m(bottom().getX(),    bottom().getY(),    coords);
      case 1: return e(hiRite().getX(), hiRite().getY(), coords);
      case 2: return e(hiLeft().getX(), hiLeft().getY(), coords);
      case 3: return e(bottom().getX(),    bottom().getY(),    coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(bottom().getX(),    bottom().getY(),    coords);
      case 1: return e(hiRite().getX(), hiRite().getY(), coords);
      case 2: return e(hiLeft().getX(), hiLeft().getY(), coords);
      case 3: return e(bottom().getX(),    bottom().getY(),    coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 4;
    }
  }

}

package org.beastmachine.ggplot.geom.point;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class ExBox implements Shape {

  private Rectangle2D bounds;
  private double x;
  private double y;
  private double halfDim;

  public ExBox(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    this.x = x;
    this.y = y;
    this.halfDim =
        0.5*sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint;
    this.bounds = new Rectangle2D.Double(
        x-halfDim, y-halfDim,2*halfDim,2*halfDim);
  }

  @Override
  public boolean contains(double x, double y) {
    return bounds.contains(x, y);
  }

  @Override
  public boolean contains(double x, double y, double w, double h) {
    return bounds.contains(x, y, w, h);
  }

  @Override
  public boolean contains(Point2D p) {
    return bounds.contains(p);
  }

  @Override
  public boolean contains(Rectangle2D r) {
    return bounds.contains(r);
  }

  @Override
  public Rectangle getBounds() {
    return bounds.getBounds();
  }

  @Override
  public Rectangle2D getBounds2D() {
    return bounds.getBounds2D();
  }

  @Override
  public boolean intersects(double x, double y, double w, double h) {
    return bounds.intersects(x, y, w, h);
  }

  @Override
  public boolean intersects(Rectangle2D r) {
    return bounds.intersects(r);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt(at);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return new PathIt(at);
  }

  private class PathIt extends PathIterators {

    private PathIt(AffineTransform at) {
      super(at);
    }

    public int currentSegment(float[] coords) {
      switch (myState) {
      case 0: return m(x-halfDim, y-halfDim, coords);
      case 1: return e(x+halfDim, y-halfDim, coords);
      case 2: return e(x+halfDim, y+halfDim, coords);
      case 3: return e(x-halfDim, y+halfDim, coords);
      case 4: return e(x-halfDim, y-halfDim, coords);
      case 5: return e(x+halfDim, y+halfDim, coords);
      case 6: return m(x+halfDim, y-halfDim, coords);
      case 7: return e(x-halfDim, y+halfDim, coords);
      }
      return 0;
    }

    public int currentSegment(double[] coords) {
      switch (myState) {
      case 0: return m(x-halfDim, y-halfDim, coords);
      case 1: return e(x+halfDim, y-halfDim, coords);
      case 2: return e(x+halfDim, y+halfDim, coords);
      case 3: return e(x-halfDim, y+halfDim, coords);
      case 4: return e(x-halfDim, y-halfDim, coords);
      case 5: return e(x+halfDim, y+halfDim, coords);
      case 6: return m(x+halfDim, y-halfDim, coords);
      case 7: return e(x-halfDim, y+halfDim, coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 8;
    }

  }
}

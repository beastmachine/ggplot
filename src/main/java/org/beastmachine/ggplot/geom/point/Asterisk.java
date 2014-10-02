package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.sqrt;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Asterisk extends Circle {

  private static final double LENGTH_CONSTANT = sqrt(2)/2.0;
  private static final double DIAG = sqrt(2)/2.0;

  public double x;
  public double y;
  private double sizeIn075mm;
  private double pixelsPerPoint;

  private double radius;

  public Asterisk(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    super(x, y, sizeIn075mm*2*LENGTH_CONSTANT, pixelsPerPoint);
    this.x = x;
    this.y = y;
    this.sizeIn075mm = sizeIn075mm;
    this.pixelsPerPoint = pixelsPerPoint;
    this.radius = radius();
  }

  private double radius() {
    return sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint*LENGTH_CONSTANT;
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
      case 1: return e(x,        y+radius, coords);
      case 2: return m(x-radius, y,        coords);
      case 3: return e(x+radius, y,        coords);
      case 4: return m(x-radius*DIAG, y-radius*DIAG, coords);
      case 5: return e(x+radius*DIAG, y+radius*DIAG, coords);
      case 6: return m(x+radius*DIAG, y-radius*DIAG, coords);
      case 7: return e(x-radius*DIAG, y+radius*DIAG, coords);
      }
      return 0;
    }

    public int currentSegment(float[] coords) {
      switch(myState) {
      case 0: return m(x,        y-radius, coords);
      case 1: return e(x,        y+radius, coords);
      case 2: return m(x-radius, y,        coords);
      case 3: return e(x+radius, y,        coords);
      case 4: return m(x-radius*DIAG, y-radius*DIAG, coords);
      case 5: return e(x+radius*DIAG, y+radius*DIAG, coords);
      case 6: return m(x+radius*DIAG, y-radius*DIAG, coords);
      case 7: return e(x-radius*DIAG, y+radius*DIAG, coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 8;
    }
  }

}

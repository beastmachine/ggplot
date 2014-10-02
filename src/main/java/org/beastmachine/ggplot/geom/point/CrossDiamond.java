package org.beastmachine.ggplot.geom.point;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.beastmachine.ggplot.visual.GeomConstants;

public class CrossDiamond extends Diamond {

  private static final double LENGTH_CONSTANT = 0.705555555555;

  public CrossDiamond(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    super(x,y,sizeIn075mm, pixelsPerPoint);
  }

  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt(at);
  }

  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return new PathIt(at);
  }

  private PathIterator getSuperPathIter(AffineTransform at) {
    return super.getPathIterator(at);
  }
  
  private class PathIt extends PathIterators {

    private PathIterator superIterator;

    private PathIt(AffineTransform at) {
      super(at);
      superIterator = CrossDiamond.this.getSuperPathIter(at);
    }

    public int getWindingRule() {
      return WIND_NON_ZERO;
    }

    public boolean isDone() {
      return superIterator.isDone() && super.isDone();
    }

    public void next() {
      if (superIterator.isDone()) {
        super.next();
      } else {
        superIterator.next();
      }
    }

    public int currentSegment(float[] coords) {
      if (superIterator.isDone()) {
        switch(myState) {
        case 0: return m(x, y-radius, coords);
        case 1: return e(x, y+radius, coords);
        case 2: return m(x-radius, y, coords);
        case 3: return e(x+radius, y, coords);
        }
      } else {
        return superIterator.currentSegment(coords);
      }
      return 0;
    }

    public int currentSegment(double[] coords) {
      if (superIterator.isDone()) {
        switch(myState) {
        case 0: return m(x, y-radius, coords);
        case 1: return e(x, y+radius, coords);
        case 2: return m(x-radius, y, coords);
        case 3: return e(x+radius, y, coords);
        }
      } else {
        int retCode = superIterator.currentSegment(coords);
        return retCode;
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 4;
    }
  }

//  private class PathIt extends PathIterators {
//
//    private PathIt(AffineTransform at) {
//      super(at);
//    }
//
//    public int currentSegment(double[] coords) {
//      switch(myState) {
//      case 0: return m(x,        y-radius, coords);
//      case 1: return e(x,        y+radius, coords);
//      case 2: return m(x-radius, y,        coords);
//      case 3: return e(x+radius, y,        coords);
//      case 4: return e(x,        y+radius, coords);
//      case 5: return e(x-radius, y,        coords);
//      case 6: return e(x,        y-radius, coords);
//      case 7: return e(x+radius, y,        coords);
//      }
//      return 0;
//    }
//
//    public int currentSegment(float[] coords) {
//      switch(myState) {
//      case 0: return m(x,        y-radius, coords);
//      case 1: return e(x,        y+radius, coords);
//      case 2: return m(x-radius, y,        coords);
//      case 3: return e(x+radius, y,        coords);
//      case 4: return e(x,        y+radius, coords);
//      case 5: return e(x-radius, y,        coords);
//      case 6: return e(x,        y-radius, coords);
//      case 7: return e(x+radius, y,        coords);
//      }
//      return 0;
//    }
//
//    @Override
//    protected int getStepCount() {
//      return 8;
//    }
//  }

}

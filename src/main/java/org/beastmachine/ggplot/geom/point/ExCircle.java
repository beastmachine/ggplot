package org.beastmachine.ggplot.geom.point;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;

import org.beastmachine.ggplot.visual.GeomConstants;

public class ExCircle extends Ellipse2D.Double {

  public ExCircle(double x, double y, double sizeIn075mm, double pixelsPerPoint) {
    super(x-0.5*sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        y-0.5*sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint,
        sizeIn075mm*GeomConstants.POINTS_PER_075_MM*pixelsPerPoint);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt(at);
  }

  @Override
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
      superIterator = ExCircle.this.getSuperPathIter(at);
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
        case 0: return m(x,       y,        coords);
        case 1: return e(x+width, y+height, coords);
        case 2: return m(x+width, y,        coords);
        case 3: return e(x,       y+height, coords);
        }
      } else {
        return superIterator.currentSegment(coords);
      }
      return 0;
    }

    public int currentSegment(double[] coords) {
      if (superIterator.isDone()) {
        switch(myState) {
        case 0: return m(x,       y,        coords);
        case 1: return e(x+width, y+height, coords);
        case 2: return m(x+width, y,        coords);
        case 3: return e(x,       y+height, coords);
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

}

package org.beastmachine.ggplot.geom.point;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

public class Ex extends Rectangle2D.Double {

  public Ex(double x, double y, double w, double h) {
    super(x,y,w,h);
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at) {
    return new PathIt();
  }

  @Override
  public PathIterator getPathIterator(AffineTransform at, double flatness) {
    return new PathIt();
  }

  private class PathIt extends PathIterators {
    
    public int currentSegment(float[] coords) {
      switch (myState) {
      case 0: return m(x,       y,        coords);
      case 1: return e(x+width, y+height, coords);
      case 2: return m(x+width, y,        coords);
      case 3: return e(x,       y+height, coords);
      }
      return 0;
    }

    public int currentSegment(double[] coords) {
      switch (myState) {
      case 0: return m(x,       y,        coords);
      case 1: return e(x+width, y+height, coords);
      case 2: return m(x+width, y,        coords);
      case 3: return e(x,       y+height, coords);
      }
      return 0;
    }

    @Override
    protected int getStepCount() {
      return 4;
    }

  }
}

package org.beastmachine.ggplot.geom.point;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;

public class CrossCircle extends Ellipse2D.Double {

  public CrossCircle(double x, double y, double w, double h) {
    super(x, y, w, h);
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
  
  private class PathIt implements PathIterator {

    private int myOwnState;
    private PathIterator superIterator;
    
    private PathIt(AffineTransform at) {
      myOwnState = 0;
      superIterator = CrossCircle.this.getSuperPathIter(at);
    }
    
    public int getWindingRule() {
      return WIND_NON_ZERO;
    }

    public boolean isDone() {
      return superIterator.isDone() && myOwnState >= 4;
    }

    public void next() {
      if (superIterator.isDone()) {
        myOwnState++;
      } else {
        superIterator.next();
      }
    }

    public int currentSegment(float[] coords) {
      if (superIterator.isDone()) {
        switch(myOwnState) {
        case 0:
          coords[0] = (float)(x+width/2);
          coords[1] = (float)y;
          return SEG_MOVETO;
        case 1:
          coords[0] = (float)(x+width/2);
          coords[1] = (float)(y+height);
          return SEG_LINETO;
        case 2:
          coords[0] = (float)x;
          coords[1] = (float)(y+height/2);
          return SEG_MOVETO;
        case 3:
          coords[0] = (float)(x+width);
          coords[1] = (float)(y+height/2);
          return SEG_LINETO;
        }
      } else {
        return superIterator.currentSegment(coords);
      }
      return 0;
    }

    public int currentSegment(double[] coords) {
      if (superIterator.isDone()) {
        switch(myOwnState) {
        case 0:
          coords[0] = (x+width/2);
          coords[1] = y;
          return SEG_MOVETO;
        case 1:
          coords[0] = (x+width/2);
          coords[1] = (y+height);
          return SEG_LINETO;
        case 2:
          coords[0] = x;
          coords[1] = (y+height/2);
          return SEG_MOVETO;
        case 3:
          coords[0] = (x+width);
          coords[1] = (y+height/2);
          return SEG_LINETO;
        }
      } else {
        int retCode = superIterator.currentSegment(coords);
        for (double d : coords) {
          System.out.print(d+"\t");
        }
        System.out.println(retCode);
        return retCode;
      }
      return 0;
    }

  }

}

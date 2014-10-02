package org.beastmachine.ggplot.geom.point;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public abstract class PathIterators implements PathIterator {

  protected int myState;
  private AffineTransform at;

  public PathIterators(AffineTransform at) {
    myState = 0;
    this.at = at;
  }

  public int getWindingRule() {
    return WIND_NON_ZERO;
  }

  public boolean isDone() {
    return myState >= getStepCount();
  }

  public void next() {
    myState++;
  }

  protected int m(double a, double b, float[] coords) {
    if (at != null) {
      Point2D tab = at.transform(new Point2D.Double(a, b), null);
      coords[0] = (float)tab.getX();
      coords[1] = (float)tab.getY();
    } else {
      coords[0] = (float)a;
      coords[1] = (float)b;
    }
    return SEG_MOVETO;
  }

  protected int e(double a, double b, float[] coords) {
    if (at != null) {
      Point2D tab = at.transform(new Point2D.Double(a, b), null);
      coords[0] = (float)tab.getX();
      coords[1] = (float)tab.getY();
    } else {
      coords[0] = (float)a;
      coords[1] = (float)b;
    }
    return SEG_LINETO;
  }

  protected int c(double a, double b, double c, double d, double e, double f, float[] coords) {
    if (at != null) {
      Point2D trans = at.transform(new Point2D.Double(a, b), null);
      coords[0] = (float)trans.getX();
      coords[1] = (float)trans.getY();
      at.transform(new Point2D.Double(c, d), trans);
      coords[2] = (float)trans.getX();
      coords[3] = (float)trans.getY();
      at.transform(new Point2D.Double(e, f), trans);
      coords[4] = (float)trans.getX();
      coords[5] = (float)trans.getY();
    } else {
      coords[0] = (float)a;
      coords[1] = (float)b;
      coords[2] = (float)c;
      coords[3] = (float)d;
      coords[4] = (float)e;
      coords[5] = (float)f;
    }
    return SEG_CUBICTO;
  }

  protected int m(double a, double b, double[] coords) {
    if (at != null) {
      Point2D tab = at.transform(new Point2D.Double(a, b), null);
      coords[0] = tab.getX();
      coords[1] = tab.getY();
    } else {
      coords[0] = a;
      coords[1] = b;
    }
    return SEG_MOVETO;
  }

  protected int e(double a, double b, double[] coords) {
    if (at != null) {
      Point2D tab = at.transform(new Point2D.Double(a, b), null);
      coords[0] = tab.getX();
      coords[1] = tab.getY();
    } else {
      coords[0] = a;
      coords[1] = b;
    }
    return SEG_LINETO;
  }

  protected int c(double a, double b, double c, double d, double e, double f, double[] coords) {
    if (at != null) {
      Point2D trans = at.transform(new Point2D.Double(a, b), null);
      coords[0] = trans.getX();
      coords[1] = trans.getY();
      at.transform(new Point2D.Double(c, d), trans);
      coords[2] = trans.getX();
      coords[3] = trans.getY();
      at.transform(new Point2D.Double(e, f), trans);
      coords[4] = trans.getX();
      coords[5] = trans.getY();
    } else {
      coords[0] = a;
      coords[1] = b;
      coords[2] = c;
      coords[3] = d;
      coords[4] = e;
      coords[5] = f;
    }
    return SEG_CUBICTO;
  }

  protected abstract int getStepCount();

}

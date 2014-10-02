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
    Point2D tab = at.transform(new Point2D.Double(a, b), null);
    coords[0] = (float)tab.getX();
    coords[1] = (float)tab.getY();
    return SEG_MOVETO;
  }
  
  protected int e(double a, double b, float[] coords) {
    Point2D tab = at.transform(new Point2D.Double(a, b), null);
    coords[0] = (float)tab.getX();
    coords[1] = (float)tab.getY();
    return SEG_LINETO;
  }
  
  protected int c(double a, double b, double c, double d, double e, double f, float[] coords) {
    Point2D trans = at.transform(new Point2D.Double(a, b), null);
    coords[0] = (float)trans.getX();
    coords[1] = (float)trans.getY();
    at.transform(new Point2D.Double(c, d), trans);
    coords[2] = (float)trans.getX();
    coords[3] = (float)trans.getY();
    at.transform(new Point2D.Double(e, f), trans);
    coords[4] = (float)trans.getX();
    coords[5] = (float)trans.getY();
    return SEG_CUBICTO;
  }
  
  protected int m(double a, double b, double[] coords) {
    Point2D trans = at.transform(new Point2D.Double(a, b), null);
    coords[0] = trans.getX();
    coords[1] = trans.getY();
    return SEG_MOVETO;
  }
  
  protected int e(double a, double b, double[] coords) {
    Point2D trans = at.transform(new Point2D.Double(a, b), null);
    coords[0] = trans.getX();
    coords[1] = trans.getY();
    return SEG_LINETO;
  }
  
  protected int c(double a, double b, double c, double d, double e, double f, double[] coords) {
    Point2D trans = at.transform(new Point2D.Double(a, b), null);
    coords[0] = trans.getX();
    coords[1] = trans.getY();
    at.transform(new Point2D.Double(c, d), trans);
    coords[2] = trans.getX();
    coords[3] = trans.getY();
    at.transform(new Point2D.Double(e, f), trans);
    coords[4] = trans.getX();
    coords[5] = trans.getY();
    return SEG_CUBICTO;
  }

  protected abstract int getStepCount();

}

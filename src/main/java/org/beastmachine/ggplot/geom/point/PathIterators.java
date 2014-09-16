package org.beastmachine.ggplot.geom.point;

import java.awt.geom.PathIterator;

public abstract class PathIterators implements PathIterator {

  protected int myState;
  
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
    coords[0] = (float)a;
    coords[1] = (float)b;
    return SEG_MOVETO;
  }
  
  protected int e(double a, double b, float[] coords) {
    coords[0] = (float)a;
    coords[1] = (float)b;
    return SEG_LINETO;
  }
  
  protected int c(double a, double b, double c, double d, double e, double f, float[] coords) {
    coords[0] = (float)a;
    coords[1] = (float)b;
    coords[2] = (float)c;
    coords[3] = (float)d;
    coords[4] = (float)e;
    coords[5] = (float)f;
    return SEG_CUBICTO;
  }
  
  protected int m(double a, double b, double[] coords) {
    coords[0] = a;
    coords[1] = b;
    return SEG_MOVETO;
  }
  
  protected int e(double a, double b, double[] coords) {
    coords[0] = a;
    coords[1] = b;
    return SEG_LINETO;
  }
  
  protected int c(double a, double b, double c, double d, double e, double f, double[] coords) {
    coords[0] = a;
    coords[1] = b;
    coords[2] = c;
    coords[3] = d;
    coords[4] = e;
    coords[5] = f;
    return SEG_CUBICTO;
  }

  protected abstract int getStepCount();

}

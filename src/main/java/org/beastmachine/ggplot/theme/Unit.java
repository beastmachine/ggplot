package org.beastmachine.ggplot.theme;

import java.util.Arrays;

public class Unit {

  private double[] bigpts;

  public Unit() { }
  
  public Unit(UnitType unit, double ... x) {
    bigpts = getBigpts(unit, x);
  }
  
  public Unit(Unit other) {
    this(UnitType.bigpts, Arrays.copyOf(other.bigpts,other.bigpts.length));
  }
  
  private double[] getBigpts(UnitType unit, double[] x) {
    double[] toReturn = Arrays.copyOf(x, x.length);
    switch (unit) {
    case bigpts:
      break;
    case cicero:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 72.27 * 1238.0 / 1157.0 * 12.0;
      }
      break;
    case cm:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 2.54;
      }
      break;
    case dida:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 72.27 * 1238.0 / 1157.0;
      }
      break;
    case inches:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0;
      }
      break;
    case lines:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 * 0.2;
      }
      break;
    case mm:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 25.4;
      }
      break;
    case picas:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 72.27 * 12.0;
      }
      break;
    case points:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 72.27;
      }
      break;
    case scaledpts:
      for (int ii=0; ii<toReturn.length; ii++) {
        toReturn[ii] *= 72.0 / 72.27 / 65536;
      }
      break;
    default:
      throw new IllegalArgumentException("Unit "+unit+" not yet implemented");
    }
    return toReturn;
  }

  /**
   * To remain consistent with grid, "bigpts" means 1/72 inch here.
   * Everywhere else, this is synonymous with "points"
   * @return
   */
  public double getBigpts() {
    return bigpts[0];
  }

  public double[] getBigptsArray() {
    return bigpts;
  }

  public double getInches() {
    return getBigpts() / 72.0;
  }

  public double[] getInchesArray() {
    double[] toReturn = Arrays.copyOf(bigpts, bigpts.length);
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] /= 72.0;
    }
    return toReturn;
  }

  public double getCm() {
    return getInches() * 2.54;
  }
  
  public double[] getCmArray() {
    double[] toReturn = getInchesArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 2.54;
    }
    return toReturn;
  }

  public double getMm() {
    return getInches() * 25.4;
  }
  
  public double[] getMmArray() {
    double[] toReturn = getInchesArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 25.4;
    }
    return toReturn;
  }

  /**
   * To remain consistent with grid, "points" here means 1/72.27 inch.
   * Everywhere else, "points" means 1/72 inch.
   * @return
   */
  public double getPoints() {
    return getInches() * 72.27;
  }

  public double[] getPointsArray() {
    double[] toReturn = getInchesArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 72.27;
    }
    return toReturn;
  }
  
  public double getPicas() {
    return getPoints() / 12.0;
  }
  
  public double[] getPicasArray() {
    double[] toReturn = getPointsArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] /= 12.0;
    }
    return toReturn;
  }
  
  public double getDida() {
    return getPoints() * 1157.0 / 1238.0;
  }

  public double[] getDidaArray() {
    double[] toReturn = getPointsArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 1157.0 / 1238.0;
    }
    return toReturn;
  }
  
  public double getCicero() {
    return getDida() / 12.0;
  }
  
  public double[] getCiceroArray() {
    double[] toReturn = getDidaArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] /= 12.0;
    }
    return toReturn;
  }

  public double getScaledpts() {
    return getPoints() * 65536;
  }
  
  public double[] getScaledptsArray() {
    double[] toReturn = getPointsArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 65536;
    }
    return toReturn;
  }
  

  public double getLines() {
    return getInches() * 5;
  }

  public double[] getLinesArray() {
    double[] toReturn = getInchesArray();
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= 5;
    }
    return toReturn;
  }
  
  public double getPixels(double pixelsPerPoint) {
    return bigpts[0]*pixelsPerPoint;
  }
  
  public double[] getPixelsArray(double pixelsPerPoint) {
    double[] toReturn = Arrays.copyOf(bigpts, bigpts.length);
    for (int ii=0; ii<toReturn.length; ii++) {
      toReturn[ii] *= pixelsPerPoint;
    }
    return toReturn;
  }

  /**
   * https://stat.ethz.ch/R-manual/R-devel/library/grid/html/unit.html
   * @author Peter
   *
   */
  public enum UnitType {
    npc, cm, inches, mm, points, picas, bigpts, dida, cicero, scaledpts,
    lines, char_, native_, snpc, strwidth, strheight, grobwidth,
    grobheight;
  }
}

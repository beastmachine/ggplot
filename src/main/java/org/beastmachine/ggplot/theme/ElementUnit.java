package org.beastmachine.ggplot.theme;

public class ElementUnit {
  
  private Unit unit;
  private double[] x;
  private Object data;
  
  public ElementUnit(Unit unit, double ... x) {
    this.unit = unit;
    this.x = x;
  }

  public ElementUnit(Unit unit, Object data, double ... x) {
    this.unit = unit;
    this.x = x;
    this.data = data;
  }

  
  /**
   * https://stat.ethz.ch/R-manual/R-devel/library/grid/html/unit.html
   * @author Peter
   *
   */
  public enum Unit {
    npc, cm, inches, mm, points, picas, bigpts, dida, cicero, scaledpts,
    lines, char_, native_, snpc, strwidth, strheight, grobwidth,
    grobheight;
  }
}

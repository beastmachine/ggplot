package org.beastmachine.ggplot.theme;

public class Unit {
  
  private UnitType unit;
  private double[] x;
  private Object data;
  
  public Unit(UnitType unit, double ... x) {
    this.unit = unit;
    this.x = x;
  }

  public Unit(UnitType unit, Object data, double ... x) {
    this.unit = unit;
    this.x = x;
    this.data = data;
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

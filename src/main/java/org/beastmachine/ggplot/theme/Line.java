package org.beastmachine.ggplot.theme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.beastmachine.ggplot.visual.Colors;

import static org.beastmachine.ggplot.visual.GeomConstants.POINTS_PER_075_MM;;

public class Line {

  private final Color color;
  private final double size;
  private final LineType lineType;
  private final LineEnd lineEnd;

  public Line(Color color, double size,
      LineType lineType, LineEnd lineEnd) {
    this.color = color;
    this.size = size;
    this.lineType = lineType;
    this.lineEnd = lineEnd;
  }

  public Stroke getStroke(double pixelsPerPoint) {
    return new BasicStroke(
        (float)(size*POINTS_PER_075_MM*pixelsPerPoint),
        lineEnd.strokeKey, lineEnd.strokeKey,
        10f, lineType.dash, 0f);
  }

  public Color getColor() {
    if (lineType == LineType.blank) {
      return Colors.invisible;
    } else {
      return color;
    }
  }

  /**
   * http://stat.ethz.ch/R-manual/R-patched/library/graphics/html/par.html
   * See "Line Type Specification"
   * @author Peter
   *
   */
  public static class LineType {
    public static final LineType blank = new LineType("11");
    public static final LineType solid = new LineType("10");
    public static final LineType dashed = new LineType("44");
    public static final LineType dotted = new LineType("13");
    public static final LineType dotdash = new LineType("1343");
    public static final LineType longdash = new LineType("73");
    public static final LineType twodash = new LineType("2262");

    private float[] dash;

    public LineType(String spec) {
      dash = new float[spec.length()];
      for (int ii=0; ii<spec.length(); ii++) {
        int val = Integer.parseInt(spec.charAt(ii)+"",16);
        dash[ii] = val;
      }
    }
    
    public float[] getDash() {
      return dash;
    }
  }

  public enum LineEnd {
    butt(BasicStroke.CAP_BUTT),
    round(BasicStroke.CAP_ROUND),
    square(BasicStroke.CAP_SQUARE);

    public final int strokeKey;

    private LineEnd(int strokeKey) {
      this.strokeKey = strokeKey;
    }
  }
}

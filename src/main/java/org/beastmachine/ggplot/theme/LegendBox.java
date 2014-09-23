package org.beastmachine.ggplot.theme;

import static java.lang.Math.max;
import static java.lang.Math.round;
import static org.beastmachine.ggplot.visual.Colors.*;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.pdf.PDF2D;
import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.theme.Line.LineType;
import org.beastmachine.ggplot.theme.Unit.UnitType;
import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.util.GDimension2D;

public class LegendBox {
  private Unit legendMargin;
  private Direction legendBox;
  private Justification legendBoxJust;
  private Legend legendSpec;

  public LegendBox(Unit legendMargin, Direction legendBox,
      Justification legendBoxJust, Legend legendSpec) {
    this.legendMargin = legendMargin;
    this.legendBox = legendBox;
    this.legendBoxJust = legendBoxJust;
    this.legendSpec = legendSpec;
  }
  
  public LegendBox() {
    this.legendMargin = new Unit(UnitType.cm, 0.2);
    this.legendBox = Direction.vertical;
    this.legendBoxJust = Justification.left;
    this.legendSpec = new Legend();
  }
  
  public Dimension2D getRequiredPointsSize(Graphics2D g,
      List<LegendData> legends) {
    double width = 0;
    double height = 0;

    double halfLine = 0.5*0.2*72; // unit(0.5, "lines") in points
    double legendMargin = this.legendMargin.getBigpts();
    List<Dimension2D> legendSizes = new ArrayList<Dimension2D>();
    for (LegendData ld : legends) {
      legendSizes.add(legendSpec.getRequiredPointsSize(g, ld.getTitle(),
          ld.getTexts()));
    }

    width += legendMargin/2;
    height += legendMargin/2;
    if (legendBox == Direction.horizontal) {
      double maxHeight = 0;
      for (int ii=0; ii<legendSizes.size(); ii++) {
        width += halfLine;
        width += legendSizes.get(ii).getWidth();
        maxHeight = max(maxHeight, legendSizes.get(ii).getHeight());
      }
      height += halfLine;
      height += maxHeight;
    } else {
      double maxWidth = 0;
      for (int ii=0; ii<legendSizes.size(); ii++) {
        height += halfLine;
        height += legendSizes.get(ii).getHeight();
        maxWidth = max(maxWidth, legendSizes.get(ii).getWidth());
      }
      width += halfLine;
      width += maxWidth;
    }
    width += halfLine;
    width += legendMargin/2;
    height += halfLine;
    height += legendMargin/2;

    return new GDimension2D(width, height);
  }

  public void paint2D(Graphics2D g, List<LegendData> legends,
      Dimension2D bounds, double pixelsPerPoint) {
    Graphics2DState state = new Graphics2DState(g);
    
    new Rect(lavender, invisible, 1, LineType.solid).paint2D(g, 
        new Rectangle2D.Double(0,0,
            bounds.getWidth(), bounds.getHeight()), pixelsPerPoint);
    if (legendBox == Direction.horizontal) {
      paint2DHorizontal(g, legends, bounds, pixelsPerPoint);
    } else {
      paint2DVertical(g, legends, bounds, pixelsPerPoint);
    }
    
    state.restore(g);
  }

  private void paint2DVertical(Graphics2D g, List<LegendData> legends,
      Dimension2D bounds, double pixelsPerPoint) {
    double halfLine = 0.5*0.2*72; // unit(0.5, "lines") in points
    double legendMargin = this.legendMargin.getBigpts();

    double innerWidth =
        (bounds.getWidth()-legendMargin-2*halfLine)*pixelsPerPoint;

    double yOffset = 0;
    yOffset += 0.5*legendMargin * pixelsPerPoint;
    double xOffset = 0.5*legendMargin * pixelsPerPoint
        + halfLine*pixelsPerPoint;
    for (LegendData ld : legends) {
      yOffset += halfLine*pixelsPerPoint;
      Dimension2D legSize = legendSpec.getRequiredPointsSize(g,
          ld.getTitle(), ld.getTexts());
      double freeWidth = innerWidth-legSize.getWidth()*pixelsPerPoint;
      if (legendBoxJust == Justification.right) {
        g.translate(xOffset+freeWidth, yOffset);
        legendSpec.paint2D(g, ld.getTitle(), ld.getTexts(),
            legSize, pixelsPerPoint);
        g.translate(-xOffset-freeWidth, -yOffset);
      } else {
        g.translate(xOffset, yOffset);
        legendSpec.paint2D(g, ld.getTitle(), ld.getTexts(),
            legSize, pixelsPerPoint);
        g.translate(-xOffset, -yOffset);
      }

      yOffset += legSize.getHeight() * pixelsPerPoint;
    }
  }

  private void paint2DHorizontal(Graphics2D g, List<LegendData> legends,
      Dimension2D bounds, double pixelsPerPoint) {
    double halfLine = 0.5*0.2*72; // unit(0.5, "lines") in points
    double legendMargin = this.legendMargin.getBigpts();

    double innerHeight =
        (bounds.getHeight()-legendMargin-2*halfLine)*pixelsPerPoint;

    double xOffset = 0;
    xOffset += 0.5*legendMargin * pixelsPerPoint;
    double yOffset = 0.5*legendMargin * pixelsPerPoint
        + halfLine*pixelsPerPoint;
    for (LegendData ld : legends) {
      xOffset += halfLine*pixelsPerPoint;
      Dimension2D legSize = legendSpec.getRequiredPointsSize(g,
          ld.getTitle(), ld.getTexts());
      double freeHeight = innerHeight-legSize.getHeight()*pixelsPerPoint;
      if (legendBoxJust == Justification.bottom) {
        g.translate(xOffset, yOffset+freeHeight);
        legendSpec.paint2D(g, ld.getTitle(), ld.getTexts(),
            legSize, pixelsPerPoint);
        g.translate(-xOffset, -yOffset-freeHeight);
      } else {
        g.translate(xOffset, yOffset);
        legendSpec.paint2D(g, ld.getTitle(), ld.getTexts(),
            legSize, pixelsPerPoint);
        g.translate(-xOffset, -yOffset);
      }

      xOffset += legSize.getWidth() * pixelsPerPoint;
    }
  }

  public enum Direction {
    horizontal,vertical;
  }

  public enum Justification {
    top, bottom, left, right;
  }
  
  public static void main(String[] args) throws FileNotFoundException {
    Dimension plotPointSize = new Dimension(432, 216);
    double pixelsPerPoint = 4.16666666666667;
    Dimension pixelSize = new Dimension(
        (int)round(plotPointSize.width*pixelsPerPoint),
        (int)round(plotPointSize.height*pixelsPerPoint));
    
    Legend spec = new Legend();
    spec.getBackground().setColor(black);
    spec.setDirection(Legend.Direction.horizontal);
    LegendBox lb = new LegendBox(new Unit(UnitType.lines,1),
        Direction.vertical, Justification.left, spec);
    
    String title1 = "Legend";
    List<String> texts1 = new ArrayList<String>();
    texts1.add("alpha");
    texts1.add("beta");
    texts1.add("camma");
    texts1.add("delta");
    
    String title2 = "Sparta";
    List<String> texts2 = new ArrayList<String>();
    texts2.add("cat");
    texts2.add("dog");
    texts2.add("elk");
    
    List<LegendData> legends = new ArrayList<LegendData>();
    legends.add(new LegendData(title1, texts1));
    legends.add(new LegendData(title2, texts2));
    
    PDF2D pdf = PaintPDF.getPdf(pixelSize, plotPointSize, "/Users/Peter/Documents/test.pdf");
    Dimension2D pointsSize = lb.getRequiredPointsSize(pdf, legends);
    lb.paint2D(pdf, legends, pointsSize, pixelsPerPoint);
    pdf.endExport();
  }
}

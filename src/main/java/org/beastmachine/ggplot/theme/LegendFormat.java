package org.beastmachine.ggplot.theme;

import static java.lang.Math.max;
import static java.lang.Math.round;
import static org.beastmachine.ggplot.visual.Colors.black;
import static org.beastmachine.ggplot.visual.Colors.blue;
import static org.beastmachine.ggplot.visual.Colors.grey50;
import static org.beastmachine.ggplot.visual.Colors.grey90;
import static org.beastmachine.ggplot.visual.Colors.grey95;
import static org.beastmachine.ggplot.visual.Colors.purple;
import static org.beastmachine.ggplot.visual.Colors.red;
import static org.beastmachine.ggplot.visual.Colors.white;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.pdf.PDF2D;
import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.theme.Line.LineType;
import org.beastmachine.ggplot.theme.TextFormat.Face;
import org.beastmachine.ggplot.theme.Unit.UnitType;
import org.beastmachine.ggplot.visual.GeomConstants;
import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.util.GDimension2D;

/*
 * Some interesting thoughts:
 * http://kohske.wordpress.com/2010/12/25/various-position-adjustments-of-legend-in-ggplot2/
 */
public class LegendFormat {

  private Rect background;
  private Direction direction;
  private TextFormat title;
  private ZeroOne titleAlign;
  private Rect key;
  private Unit keyWidth;
  private Unit keyHeight;
  private TextFormat text;
  private ZeroOne textAlign;

  public LegendFormat(Rect background, Direction direction, TextFormat title,
      ZeroOne titleAlign, Rect key, Unit keyWidth, Unit keyHeight,
      TextFormat text, ZeroOne textAlign) {
    this.background = background;
    this.direction = direction;
    this.title = title;
    this.titleAlign = titleAlign;
    this.key = key;
    this.keyWidth = keyWidth;
    this.keyHeight = keyHeight;
    this.text = text;
    this.textAlign = textAlign;
  }

  public LegendFormat() {
    this.background = new Rect(white, black, 0.5, LineType.solid);
    this.direction = Direction.vertical;
    this.title = new TextFormat("Arial", Face.bold, black, 9.6, new ZeroOne(0),
        new ZeroOne(0), 0, 1);
    this.titleAlign = new ZeroOne(0);
    this.key = new Rect(grey95,white,0.5,LineType.solid);
    this.keyWidth = new Unit(UnitType.lines, 1.2);
    this.keyHeight = new Unit(UnitType.lines, 1.2);
    this.text = new TextFormat("Arial", Face.plain, black, 9.6, new ZeroOne(0),
        new ZeroOne(0), 0, 1);
    this.textAlign = new ZeroOne(0);
  }

  public enum Direction {
    horizontal,vertical;
  }

  public Dimension2D getRequiredPointsSize(Graphics2D g, String title, List<String> texts) {
    double width = 0;
    double height = 0;

    double outGap = GeomConstants.POINTS_PER_075_MM * 2.0;
    double inGap = 0.2*0.2*72; // unit(0.2, "lines") in points

    Font titleFont = this.title.getFont(1);
    Font textFont = this.text.getFont(1);

    if (direction == Direction.horizontal) {
      width += outGap;
      width += getStringWidth(title, titleFont, g);
      width += inGap;
      for (String text : texts) {
        width += inGap;
        width += this.keyWidth.getBigpts();
        width += inGap;
        width += getStringWidth(text, textFont, g);
      }
      width += outGap;

      double keyHeight = this.keyHeight.getBigpts();
      double titleHeight = getStringHeight(title, titleFont, g);
      double textHeight = 0;
      for (String text : texts) {
        textHeight = max(textHeight, getStringHeight(text, textFont, g));
      }
      height += outGap;
      height += max(keyHeight,max(titleHeight,textHeight));
      height += outGap;
    } else {
      height += outGap;
      height += getStringHeight(title, titleFont, g);
      height += inGap * 2;
      for (String text : texts) {
        double keyHeight = this.keyHeight.getBigpts();
        double textHeight = getStringHeight(text, textFont, g);
        height += max(keyHeight,textHeight);
      }
      height += outGap;

      double titleWidth = getStringWidth(title, titleFont, g);
      double maxKeyWidth = 0;
      for (String text : texts) {
        double kw = 0;
        kw += this.keyWidth.getBigpts();
        kw += 2 * inGap;
        kw += getStringWidth(text, textFont, g);
        maxKeyWidth = max(maxKeyWidth, kw);
      }
      width += outGap;
      width += max(titleWidth, maxKeyWidth);
      width += outGap;
    }
    return new GDimension2D(width, height);
  }

  public void paint2D(Graphics2D g, String title, List<String> texts,
      Dimension2D pointSize, double pixelsPerPoint) {
    Graphics2DState state = new Graphics2DState(g);

    Rectangle2D pointBounds = new Rectangle2D.Double(
        0,0,pointSize.getWidth(), pointSize.getHeight());
    background.paint2D(g, pointBounds, pixelsPerPoint);
    if (direction==Direction.horizontal) {
      paint2DHorizontal(g,title,texts,pointSize,pixelsPerPoint);
    } else {
      paint2DVertical(g,title,texts,pointSize,pixelsPerPoint);
    }

    state.restore(g);
  }

  private void paint2DVertical(Graphics2D g, String title, List<String> texts,
      Dimension2D pointSize, double pixelsPerPoint) {
    Font titleFont = this.title.getFont(pixelsPerPoint);
    Font textFont = this.text.getFont(pixelsPerPoint);

    g.setFont(titleFont);
    g.setColor(this.title.getColor());

    double outGap = GeomConstants.POINTS_PER_075_MM * 2.0;
    double inGap = 0.2*0.2*72; // unit(0.2, "lines") in points

    double titleHeight = getStringHeight(title, titleFont, g);
    double innerWidth = (pointSize.getWidth() - 2*outGap)*pixelsPerPoint;

    double xOffset = outGap * pixelsPerPoint;
    double yOffset = outGap * pixelsPerPoint;
    drawString(g, title,
        new Rectangle2D.Double(xOffset, yOffset, innerWidth, titleHeight),
        titleAlign);
    yOffset += titleHeight;
    yOffset += 2 * inGap * pixelsPerPoint;

    g.setFont(textFont);
    g.setColor(this.text.getColor());
    double textXOffset = xOffset
        + this.keyWidth.getBigpts() * pixelsPerPoint
        + 2 * inGap * pixelsPerPoint;
    for (String text : texts) {
      double textHeight = getStringHeight(text, textFont, g);
      double textWidth = getStringWidth(text, textFont, g);
      double keyHeight = this.keyHeight.getBigpts()*pixelsPerPoint;
      double lineHeight = max(textHeight, keyHeight);

      g.translate(xOffset, yOffset);
      key.paint2D(g, new Rectangle2D.Double(0, 0,
          this.keyWidth.getBigpts(), lineHeight/pixelsPerPoint),
          pixelsPerPoint);
      g.translate(-xOffset, -yOffset);

      double yCenter = yOffset + lineHeight/2;
      drawString(g, text,
          new Rectangle2D.Double(textXOffset, yCenter-textHeight/2,
              textWidth, textHeight), textAlign);
      yOffset += lineHeight;
    }
  }

  private void paint2DHorizontal(Graphics2D g, String title,
      List<String> texts, Dimension2D pointSize, double pixelsPerPoint) {
    Font titleFont = this.title.getFont(pixelsPerPoint);
    Font textFont = this.text.getFont(pixelsPerPoint);

    g.setFont(titleFont);
    g.setColor(this.title.getColor());

    double outGap = GeomConstants.POINTS_PER_075_MM * 2.0;
    double inGap = 0.2*0.2*72; // unit(0.2, "lines") in points

    double offset = outGap * pixelsPerPoint;
    double yCenter = pointSize.getHeight()/2 * pixelsPerPoint;
    double titleHeight = getStringHeight(title, titleFont, g);
    double titleWidth = getStringWidth(title, titleFont, g);
    drawString(g, title,
        new Rectangle2D.Double(offset, yCenter-titleHeight/2, titleWidth, titleHeight),
        titleAlign);
    offset += titleWidth;
    offset += inGap * pixelsPerPoint;

    double keyW = keyWidth.getBigpts();
    double keyH = keyHeight.getBigpts();
    double keyY = yCenter/pixelsPerPoint - keyH/2;

    g.setFont(textFont);
    g.setColor(this.text.getColor());
    for (String text : texts) {
      double textHeight = getStringHeight(text, textFont, g);
      double textWidth = getStringWidth(text, textFont, g);
      offset += inGap * pixelsPerPoint;
      g.translate(offset,0);
      key.paint2D(g, new Rectangle2D.Double(0, keyY, keyW, keyH), pixelsPerPoint);
      g.translate(-offset,0);

      offset += keyW * pixelsPerPoint;
      offset += inGap * pixelsPerPoint;
      drawString(g, text, new Rectangle2D.Double(offset, yCenter-textHeight/2, textWidth, textHeight), textAlign);
      offset += getStringWidth(text, textFont, g);
    }
  }

  private double getStringHeight(String str, Font f, Graphics2D g) {
    String[] lines = str.split("\n");
    return lines.length*f.getLineMetrics(str, g.getFontRenderContext()).getHeight();
  }

  private double getStringAscent(String str, Font f, Graphics2D g) {
    String[] lines = str.split("\n");
    return f.getLineMetrics(lines[0], g.getFontRenderContext()).getAscent();
  }

  private double getStringWidth(String str, Font f, Graphics2D g) {
    String[] lines = str.split("\n");
    double width = 0;
    for (String line : lines) {
      width = max(width, f.getStringBounds(line, g.getFontRenderContext()).getWidth());
    }
    return width;
  }

  private void drawString(Graphics2D g, String str, Rectangle2D bounds,
      ZeroOne hjust) {
    String[] lines = str.split("\n");
    double lineHeight = g.getFont().getLineMetrics(str, g.getFontRenderContext()).getHeight();
    for (int ii=0; ii<lines.length; ii++) {
      String line = lines[ii];
      double width = getStringWidth(line, g.getFont(), g);
      double ascent = getStringAscent(line, g.getFont(), g);
      double excessWidth = bounds.getWidth()-width;
      double strX = bounds.getX() + hjust.val*excessWidth;
      double strY = bounds.getY() + ascent + lineHeight*ii;
      g.drawString(line,(float)strX, (float)strY);
    }
  }
  public Rect getBackground() {
    return background;
  }

  public void setBackground(Rect background) {
    this.background = background;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public TextFormat getTitle() {
    return title;
  }

  public void setTitle(TextFormat title) {
    this.title = title;
  }

  public ZeroOne getTitleAlign() {
    return titleAlign;
  }

  public void setTitleAlign(ZeroOne titleAlign) {
    this.titleAlign = titleAlign;
  }

  public Rect getKey() {
    return key;
  }

  public void setKey(Rect key) {
    this.key = key;
  }

  public Unit getKeyWidth() {
    return keyWidth;
  }

  public void setKeyWidth(Unit keyWidth) {
    this.keyWidth = keyWidth;
  }

  public Unit getKeyHeight() {
    return keyHeight;
  }

  public void setKeyHeight(Unit keyHeight) {
    this.keyHeight = keyHeight;
  }

  public TextFormat getText() {
    return text;
  }

  public void setText(TextFormat text) {
    this.text = text;
  }

  public ZeroOne getTextAlign() {
    return textAlign;
  }

  public void setTextAlign(ZeroOne textAlign) {
    this.textAlign = textAlign;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Dimension plotPointSize = new Dimension(576, 216);
    double pixelsPerPoint = 4.16666666666667;
    Dimension pixelSize = new Dimension(
        (int)round(plotPointSize.width*pixelsPerPoint),
        (int)round(plotPointSize.height*pixelsPerPoint));

    LegendFormat leg = new LegendFormat(
        new Rect(grey50, black, 1, LineType.dashed),
        Direction.vertical,
        new TextFormat("sans", Face.plain, red, 12, new ZeroOne(0), new ZeroOne(0), 0, 1),
        new ZeroOne(0.5),
        new Rect(grey90, purple, 0.5, LineType.solid),
        new Unit(UnitType.cm, 1.5),
        new Unit(UnitType.cm, 1),
        new TextFormat("serif", Face.bold, blue, 8, new ZeroOne(1), new ZeroOne(1), 0, 0),
        new ZeroOne(0.5));
    leg = new LegendFormat();
    leg.setTitleAlign(new ZeroOne(1));
    String title = "Legend";
    List<String> texts = new ArrayList<String>();
    texts.add("alpha");
    texts.add("beta");
    texts.add("camma");
    texts.add("delta");

    PDF2D pdf = PaintPDF.getPdf(pixelSize, plotPointSize, "/Users/Peter/Documents/test.pdf");

    Dimension2D pointsSize = leg.getRequiredPointsSize(pdf, title, texts);
    
    pdf.translate(200,200);
    leg.paint2D(pdf, title, texts, pointsSize, pixelsPerPoint);
    pdf.translate(-200,-200);
    
    pdf.translate(500,200);
    leg.setDirection(Direction.horizontal);
    pointsSize = leg.getRequiredPointsSize(pdf, title, texts);
    leg.paint2D(pdf, title, texts, pointsSize, pixelsPerPoint);
    pdf.translate(-500,-200);
    
    pdf.endExport();
  }

}

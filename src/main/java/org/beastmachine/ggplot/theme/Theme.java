package org.beastmachine.ggplot.theme;
import static org.beastmachine.ggplot.GGPlot.axisLine;
import static org.beastmachine.ggplot.GGPlot.axisText;
import static org.beastmachine.ggplot.GGPlot.axisTextX;
import static org.beastmachine.ggplot.GGPlot.axisTextY;
import static org.beastmachine.ggplot.GGPlot.axisTicks;
import static org.beastmachine.ggplot.GGPlot.axisTicksLength;
import static org.beastmachine.ggplot.GGPlot.axisTicksMargin;
import static org.beastmachine.ggplot.GGPlot.axisTitleX;
import static org.beastmachine.ggplot.GGPlot.axisTitleY;
import static org.beastmachine.ggplot.GGPlot.legendBackground;
import static org.beastmachine.ggplot.GGPlot.legendJustification;
import static org.beastmachine.ggplot.GGPlot.legendKey;
import static org.beastmachine.ggplot.GGPlot.legendKeySize;
import static org.beastmachine.ggplot.GGPlot.legendMargin;
import static org.beastmachine.ggplot.GGPlot.legendPosition;
import static org.beastmachine.ggplot.GGPlot.legendText;
import static org.beastmachine.ggplot.GGPlot.legendTitle;
import static org.beastmachine.ggplot.GGPlot.line;
import static org.beastmachine.ggplot.GGPlot.panelBackground;
import static org.beastmachine.ggplot.GGPlot.panelBorder;
import static org.beastmachine.ggplot.GGPlot.panelGridMajor;
import static org.beastmachine.ggplot.GGPlot.panelGridMinor;
import static org.beastmachine.ggplot.GGPlot.panelMargin;
import static org.beastmachine.ggplot.GGPlot.plotBackground;
import static org.beastmachine.ggplot.GGPlot.plotMargin;
import static org.beastmachine.ggplot.GGPlot.plotTitle;
import static org.beastmachine.ggplot.GGPlot.rect;
import static org.beastmachine.ggplot.GGPlot.stripBackground;
import static org.beastmachine.ggplot.GGPlot.stripText;
import static org.beastmachine.ggplot.GGPlot.stripTextX;
import static org.beastmachine.ggplot.GGPlot.stripTextY;
import static org.beastmachine.ggplot.GGPlot.text;
import static org.beastmachine.ggplot.theme.Justification.center;
import static org.beastmachine.ggplot.theme.Line.LineEnd.butt;
import static org.beastmachine.ggplot.theme.Line.LineType.solid;
import static org.beastmachine.ggplot.theme.Position.right;
import static org.beastmachine.ggplot.theme.TextFormat.Face.bold;
import static org.beastmachine.ggplot.theme.TextFormat.Face.plain;
import static org.beastmachine.ggplot.theme.Unit.UnitType.cm;
import static org.beastmachine.ggplot.theme.Unit.UnitType.lines;
import static org.beastmachine.ggplot.visual.Colors.black;
import static org.beastmachine.ggplot.visual.Colors.grey50;
import static org.beastmachine.ggplot.visual.Colors.grey80;
import static org.beastmachine.ggplot.visual.Colors.grey90;
import static org.beastmachine.ggplot.visual.Colors.grey95;
import static org.beastmachine.ggplot.visual.Colors.invisible;
import static org.beastmachine.ggplot.visual.Colors.white;

import java.util.HashMap;
import java.util.Map;

public class Theme {

  private static final Theme THEME_GREY;
  static{
    THEME_GREY = theme(
        line().color(black).size(0.5).lineType(solid).lineEnd(butt),
        rect().fill(white).color(black).size(0.5).lineType(solid),
        text().family("Arial").face(plain).color(black).size(12)
        .hjust(new ZeroOne(0.5)).vjust(new ZeroOne(0.5)).angle(0)
        .lineheight(0.9),

        axisLine().blank(),
        axisText().size(9.6).color(grey50),
        axisTextX().vjust(new ZeroOne(1)),
        axisTextY().hjust(new ZeroOne(1)),
        axisTicks().color(grey50),
        axisTitleX(),
        axisTitleY().angle(90),
        axisTicksLength().unit(cm, 0.15),
        axisTicksMargin().unit(cm, 0.1),

        legendBackground().color(invisible),
        legendMargin().unit(cm, 0.2),
        legendKey().fill(grey95).color(white),
        legendKeySize().unit(lines, 1.2),
        legendText().size(9.6),
        legendTitle().size(9.6).face(bold).hjust(new ZeroOne(0)),
        legendPosition().position(right),
        legendJustification().justification(center),

        panelBackground().fill(grey90).color(invisible),
        panelBorder().blank(),
        panelGridMajor().color(white),
        panelGridMinor().color(grey95).size(0.25),
        panelMargin().unit(lines,0.25),

        stripBackground().fill(grey80).color(invisible),
        stripText().size(9.6),
        stripTextX(),
        stripTextY().angle(-90),

        plotBackground().color(white),
        plotTitle().size(14.4),
        plotMargin().unit(lines, 1,1,0.5,0.5)
        );
  }

  public Map<Key, Object> themeValues;

  public Theme() {
    themeValues = new HashMap<Theme.Key, Object>();
    for (KeyLine key : KeyLine.values()) {
      this.put(key, new Line());
    }
    for (KeyRect key : KeyRect.values()) {
      this.put(key, new Rect());
    }
    for (KeyText key : KeyText.values()) {
      this.put(key, new TextFormat());
    }
    for (KeyUnit key : KeyUnit.values()) {
      this.put(key, new Unit());
    }
    for (KeyZeroOne key : KeyZeroOne.values()) {
      this.put(key, new ZeroOne());
    }
    for (KeyDirection key : KeyDirection.values()) {
      this.put(key, Direction.horizontal);
    }
    for (KeyPosition key : KeyPosition.values()) {
      this.put(key, Position.right);
    }
    for (KeyJustification key : KeyJustification.values()) {
      this.put(key, Justification.center);
    }
  }

  public Theme(Theme other) {
    this();
    for (KeyLine key : KeyLine.values()) {
      this.put(key, new Line(other.get(key)));
    }
    for (KeyRect key : KeyRect.values()) {
      this.put(key, new Rect(other.get(key)));
    }
    for (KeyText key : KeyText.values()) {
      this.put(key, new TextFormat(other.get(key)));
    }
    for (KeyUnit key : KeyUnit.values()) {
      this.put(key, new Unit(other.get(key)));
    }
    for (KeyZeroOne key : KeyZeroOne.values()) {
      this.put(key, new ZeroOne(other.get(key)));
    }
    for (KeyDirection key : KeyDirection.values()) {
      this.put(key, other.get(key));
    }
    for (KeyPosition key : KeyPosition.values()) {
      this.put(key, other.get(key));
    }
    for (KeyJustification key : KeyJustification.values()) {
      this.put(key, other.get(key));
    }
  }

  public static Theme themeGrey() {
    return new Theme(THEME_GREY);
  }
  
  private static Theme theme(ThemeOptionSetter ... options) {
    Theme theme = new Theme();
    for (ThemeOptionSetter option : options) {
      option.setOptions(theme);
    }
    return theme;
  }

  public Line get(KeyLine key) {
     Object line = themeValues.get(key);
     if(line != null && line instanceof Line){
       return (Line)line;
     }
     else return null;//TODO return some default line?
  }

  public void put(KeyLine key, Line elem) {
    themeValues.put(key, elem);
  }

  public Rect get(KeyRect key) {
    Object rect = themeValues.get(key);
    if(rect != null && rect instanceof Rect){
      return (Rect)rect;
    }
    else return null;//TODO return some default Rect?
  }

  public void put(KeyRect key, Rect elem) {
    themeValues.put(key, elem);
  }

  public TextFormat get(KeyText key) {
    Object text = themeValues.get(key);
    if(text != null && text instanceof TextFormat){
      return (TextFormat)text;
    }
    else return null;//TODO return some default Text?
  }

  public void put(KeyText key, TextFormat elem) {
    themeValues.put(key, elem);
  }

  public Unit get(KeyUnit key) {
    Object unit = themeValues.get(key);
    if(unit != null && unit instanceof Unit){
      return (Unit)unit;
    }
    else return null;//TODO return some default Unit?
  }

  public void put(KeyUnit key, Unit elem) {
    themeValues.put(key, elem);
  }

  public ZeroOne get(KeyZeroOne key) {
    Object zeroOne = themeValues.get(key);
    if(zeroOne != null && zeroOne instanceof ZeroOne){
      return (ZeroOne)zeroOne;
    }
    else return null;
  }

  public void put(KeyZeroOne key, ZeroOne elem) {
    themeValues.put(key, elem);
  }

  public Direction get(KeyDirection key) {
    Object dir = themeValues.get(key);
    if(dir != null && dir instanceof Direction){
      return (Direction)dir;
    }
    else return null;//return some default direction?
  }

  public void put(KeyDirection key, Direction elem) {
    themeValues.put(key, elem);
  }

  public Position get(KeyPosition key) {
    Object pos = themeValues.get(key);
    if(pos != null && pos instanceof Position){
      return (Position)pos;
    }
    else return null;//return some default position??????? this may be harder, maybe just error
  }

  public void put(KeyPosition key, Position elem) {
    themeValues.put(key, elem);
  }

  public Justification get(KeyJustification key) {
    Object just = themeValues.get(key);
    if(just != null && just instanceof Justification){
      return (Justification)just;
    }
    else return null;//return some defualt justification?
  }

  public void put(KeyJustification key, Justification elem) {
    themeValues.put(key, elem);
  }

  private static class ImmutableTheme extends Theme {

    private ImmutableTheme(Theme src) {
      this.themeValues = new HashMap<Theme.Key, Object>();
      this.themeValues.putAll(src.themeValues);
    }

    @Override
    public void put(KeyLine key, Line elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyRect key, Rect elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyText key, TextFormat elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }


    @Override
    public void put(KeyUnit key, Unit elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyZeroOne key, ZeroOne elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyDirection key, Direction elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyPosition key, Position elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }

    @Override
    public void put(KeyJustification key, Justification elem) {
      System.err.println("Warning: theme immutable, put command ignored");
    }
  }

  public interface Key {}

  public enum KeyLine implements Key {
    //line, axis_ticks, 
    axis_ticks_x, axis_ticks_y, //axis_line,
    axis_line_x, axis_line_y, 
    //panel_grid, panel_grid_major, panel_grid_minor,
    panel_grid_major_x, panel_grid_major_y,
    panel_grid_minor_x, panel_grid_minor_y
  }

  public enum KeyRect implements Key {
    //rect, 
    legend_background, legend_key, panel_background, panel_border,
    plot_background, strip_background
  }

  public enum KeyText implements Key {
    //text, title, axis_title,
    axis_title_x, axis_title_y,// axis_text,
    axis_text_x, axis_text_y, legend_text, legend_title, plot_title,
    //strip_text,
    strip_text_x, strip_text_y
  }

  public enum KeyUnit implements Key {
    axis_ticks_length, axis_ticks_margin, legend_margin,
    //legend_key_size,
    legend_key_height, legend_key_width, panel_margin,
    plot_margin
  }

  public enum KeyZeroOne implements Key {
    legend_text_align, legend_title_align
  }

  public enum KeyDirection implements Key {
    legend_direction, legend_box
  }

  public enum KeyPosition implements Key {
    legend_position, legend_box_just
  }

  public enum KeyJustification implements Key {
    legend_justification
  }
}

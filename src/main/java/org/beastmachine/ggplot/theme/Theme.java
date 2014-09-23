package org.beastmachine.ggplot.theme;

import static org.beastmachine.ggplot.theme.Line.LineType.*;
import static org.beastmachine.ggplot.theme.Line.LineEnd.*;
import static org.beastmachine.ggplot.theme.Text.Face.*;
import static org.beastmachine.ggplot.visual.Colors.*;

import java.util.Map;
import java.util.TreeMap;

public class Theme {

  public static final Theme THEME_GREY;
  static{
    Theme tmp = new Theme();
    tmp.put(KeyLine.line, new Line(black,0.5, solid, butt));
    tmp.put(KeyRect.rect, new Rect(white, black, 0.5, solid));
    tmp.put(KeyText.text, new Text("", plain, black, 12, new ZeroOne(0.5), new ZeroOne(0.5), 0, 0.9));

    tmp.put(KeyLine.axis_line, null);
//    tmp.put(KeyText.axis_text, )
    THEME_GREY = new ImmutableTheme(tmp);
  }

  public Map<Key, Object> themeValues;

  public Theme() {
    themeValues = new TreeMap<Theme.Key, Object>();
  }

  public Line get(KeyLine key) {
    return (Line)themeValues.get(key);
  }

  public void put(KeyLine key, Line elem) {
    themeValues.put(key, elem);
  }

  public Rect get(KeyRect key) {
    return (Rect)themeValues.get(key);
  }

  public void put(KeyRect key, Rect elem) {
    themeValues.put(key, elem);
  }

  public Text get(KeyText key) {
    return (Text)themeValues.get(key);
  }

  public void put(KeyText key, Text elem) {
    themeValues.put(key, elem);
  }

  public Unit get(KeyUnit key) {
    return (Unit)themeValues.get(key);
  }

  public void put(KeyUnit key, Unit elem) {
    themeValues.put(key, elem);
  }

  public ZeroOne get(KeyZeroOne key) {
    return (ZeroOne)themeValues.get(key);
  }

  public void put(KeyZeroOne key, ZeroOne elem) {
    themeValues.put(key, elem);
  }

  public Direction get(KeyDirection key) {
    return (Direction)themeValues.get(key);
  }

  public void put(KeyDirection key, Direction elem) {
    themeValues.put(key, elem);
  }

  public Position get(KeyPosition key) {
    return (Position)themeValues.get(key);
  }

  public void put(KeyPosition key, Position elem) {
    themeValues.put(key, elem);
  }

  public Justification get(KeyJustification key) {
    return (Justification)themeValues.get(key);
  }

  public void put(KeyJustification key, Justification elem) {
    themeValues.put(key, elem);
  }

  private static class ImmutableTheme extends Theme {

    private ImmutableTheme(Theme src) {
      this.themeValues = new TreeMap<Theme.Key, Object>();
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
    public void put(KeyText key, Text elem) {
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
    line, axis_ticks, axis_ticks_x, axis_ticks_y, axis_line,
    axis_line_x, axis_line_y, panel_grid, panel_grid_major,
    panel_grid_minor, panel_grid_major_x, panel_grid_major_y,
    panel_grid_minor_x, panel_grid_minor_y
  }

  public enum KeyRect implements Key {
    rect, legend_background, legend_key, panel_background, panel_border,
    plot_background, strip_background
  }

  public enum KeyText implements Key {
    text, title, axis_title, axis_title_x, axis_title_y, axis_text,
    axis_text_x, axis_text_y, legend_text, legend_title, plot_title,
    strip_text, strip_text_x, strip_text_y
  }

  public enum KeyUnit implements Key {
    axis_ticks_length, axis_ticks_margin, legend_margin,
    legend_key_size, legend_key_height, legend_key_width, panel_margin,
    plot_margin
  }

  public enum KeyZeroOne implements Key {
    legend_text_align, legend_title_align
  }

  public enum KeyDirection implements Key {
    legend_direction, legend_box
  }

  public enum KeyPosition implements Key {
    legend_position
  }

  public enum KeyJustification implements Key {
    legend_justification
  }
}

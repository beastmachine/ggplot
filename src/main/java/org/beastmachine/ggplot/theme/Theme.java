package org.beastmachine.ggplot.theme;

import java.util.Map;

public class Theme {
  public Map<Key, Object> themeValues;
  
  public ElementLine get(KeyLine key) {
    return (ElementLine)themeValues.get(key);
  }
  
  public void put(KeyLine key, ElementLine elem) {
    themeValues.put(key, elem);
  }
  
  public ElementRect get(KeyRect key) {
    return (ElementRect)themeValues.get(key);
  }
  
  public void put(KeyRect key, ElementRect elem) {
    themeValues.put(key, elem);
  }
  
  public ElementText get(KeyText key) {
    return (ElementText)themeValues.get(key);
  }
  
  public void put(KeyText key, ElementText elem) {
    themeValues.put(key, elem);
  }
  
  public ElementUnit get(KeyUnit key) {
    return (ElementUnit)themeValues.get(key);
  }
  
  public void put(KeyUnit key, ElementUnit elem) {
    themeValues.put(key, elem);
  }
  
  public ElementZeroOne get(KeyZeroOne key) {
    return (ElementZeroOne)themeValues.get(key);
  }
  
  public void put(KeyZeroOne key, ElementZeroOne elem) {
    themeValues.put(key, elem);
  }
  
  public ElementDirection get(KeyDirection key) {
    return (ElementDirection)themeValues.get(key);
  }
  
  public void put(KeyDirection key, ElementDirection elem) {
    themeValues.put(key, elem);
  }
  
  public ElementPosition get(KeyPosition key) {
    return (ElementPosition)themeValues.get(key);
  }
  
  public void put(KeyPosition key, ElementPosition elem) {
    themeValues.put(key, elem);
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

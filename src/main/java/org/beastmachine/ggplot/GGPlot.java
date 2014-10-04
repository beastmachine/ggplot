package org.beastmachine.ggplot;

import static java.lang.Math.round;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.coord.Coord;
import org.beastmachine.ggplot.coord.CoordCartesian;
import org.beastmachine.ggplot.facet.Facet;
import org.beastmachine.ggplot.facet.FacetNone;
import org.beastmachine.ggplot.geom.point.GeometryPoint;
import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.theme.Line;
import org.beastmachine.ggplot.theme.Theme;
import org.beastmachine.ggplot.theme.ThemeDirectionOptionSetter;
import org.beastmachine.ggplot.theme.ThemeJustificationOptionSetter;
import org.beastmachine.ggplot.theme.ThemeLineOptionSetter;
import org.beastmachine.ggplot.theme.ThemeOptionSetter;
import org.beastmachine.ggplot.theme.ThemePositionOptionSetter;
import org.beastmachine.ggplot.theme.ThemeRectOptionSetter;
import org.beastmachine.ggplot.theme.ThemeTextOptionSetter;
import org.beastmachine.ggplot.theme.ThemeUnitOptionSetter;
import org.beastmachine.ggplot.theme.ThemeZeroOneOptionSetter;
import org.beastmachine.ggplot.theme.Theme.KeyUnit;
import org.beastmachine.ggplot.visual.Paintable;

public class GGPlot implements Paintable{

  public interface GGPlotOptionSetter extends GlobalOptionSetter{

  }

  private Scale myScale;
  private Coord myCoord;
  private Facet myFacet;
  private List<Layer> myLayers;
  private Theme theme;
  private Aes myAes;
  private DataFrame myData;

  public GGPlot() {
    theme = Theme.themeGrey();
    myCoord = new CoordCartesian();
    myLayers = new ArrayList<Layer>();
    myScale = new Scale(myCoord, myLayers);
    myFacet = new FacetNone(theme, myScale, myCoord, myLayers);
  }

  public static GGPlot ggplot(GGPlotOptionSetter... options){
    GGPlot plot = new GGPlot();
    for(GGPlotOptionSetter opt: options){
      opt.setOptions(plot);
    }
    return plot;
  }

  public static Aes aes(){
    return new Aes();
  }

  public GGPlot geom_point(GeometryPoint.OptionSetter... options){
    Layer layer = new Layer(myData, myAes, new GeometryPoint());
    for(GeometryPoint.OptionSetter setter: options){
      setter.setOptions(layer);
    }
    addLayer(layer);
    return this;
  }

  public GGPlot themeGrey() {
    this.theme = Theme.themeGrey();
    return this;
  }
  
  public GGPlot theme(ThemeOptionSetter ... options) {
    for (ThemeOptionSetter opt : options) {
      opt.setOptions(this.theme);
    }
    return this;
  }

  private void addLayer(Layer layer) {
    myLayers.add(layer);
    myScale.addLayer(layer);
  }

  public GGPlot plot(){
    JFrame frame = new JFrame();
    frame.setSize(new Dimension(800,600));
    JPanel panel = new JPanel();
    frame.setContentPane(panel);
    //TODO not sure what to do
    return this;
  }

  public static void ggsave(GGPlot g, String file){
    try {
      PaintPDF.paintToPDF(g, new Dimension(640,480),
          new Dimension(792,612), file); //TODO pull these numbers from somewhere
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
    double pixelsPerPoint = (double)pixels.getWidth() / (double)points.getWidth();

    double[] margin = theme.get(KeyUnit.plot_margin).getPixelsArray(pixelsPerPoint);

    int topMarginPixels = (int)round(margin[0]);
    int rightMarginPixels = (int)round(margin[1]);
    int bottomMarginPixels = (int)round(margin[2]);
    int leftMarginPixels = (int)round(margin[3]);

    myFacet.setArea(leftMarginPixels, (int)(pixels.getWidth() - rightMarginPixels), 
        topMarginPixels, (int)(pixels.getHeight() - bottomMarginPixels));

    myFacet.paint2D(g, pixels, points);
  }


  /**
   * Stuff included for concise syntax after doing import static org.beastmachine.ggplot.GGPlot.*;
   */
  public static DataFrame dataframe(){
    return new DataFrame();
  }
  
  public interface Scalable {
    public int xDataPointToPixelLocation(double d);
    public int yDataPointToPixelLocation(double d);
  }

  public static final Layer.Geom geom_abline = Layer.Geom.geom_abline;
  public static final Layer.Geom geom_area = Layer.Geom.geom_area;
  public static final Layer.Geom geom_bar = Layer.Geom.geom_bar;
  public static final Layer.Geom geom_bin2d = Layer.Geom.geom_bin2d;
  public static final Layer.Geom geom_boxplot = Layer.Geom.geom_boxplot;
  public static final Layer.Geom geom_contour = Layer.Geom.geom_contour;
  public static final Layer.Geom geom_crossbar = Layer.Geom.geom_crossbar;
  public static final Layer.Geom geom_freqpoly = Layer.Geom.geom_freqpoly;
  public static final Layer.Geom geom_hex = Layer.Geom.geom_hex;
  public static final Layer.Geom geom_histogram = Layer.Geom.geom_histogram;
  public static final Layer.Geom geom_hline = Layer.Geom.geom_hline;
  public static final Layer.Geom geom_jitter = Layer.Geom.geom_jitter;
  public static final Layer.Geom geom_line = Layer.Geom.geom_line;
  public static final Layer.Geom geom_linerange = Layer.Geom.geom_linerange;
  public static final Layer.Geom geom_map = Layer.Geom.geom_map;
  public static final Layer.Geom geom_path = Layer.Geom.geom_path;
  public static final Layer.Geom geom_point = Layer.Geom.geom_point;
  public static final Layer.Geom geom_pointrange = Layer.Geom.geom_pointrange;
  public static final Layer.Geom geom_polygon = Layer.Geom.geom_polygon;
  public static final Layer.Geom geom_quantile = Layer.Geom.geom_quantile;
  public static final Layer.Geom geom_raster = Layer.Geom.geom_raster;
  public static final Layer.Geom geom_rect = Layer.Geom.geom_rect;
  public static final Layer.Geom geom_ribbon = Layer.Geom.geom_ribbon;	
  public static final Layer.Geom geom_rug = Layer.Geom.geom_rug;
  public static final Layer.Geom geom_segment = Layer.Geom.geom_segment;
  public static final Layer.Geom geom_smooth = Layer.Geom.geom_smooth;
  public static final Layer.Geom geom_step = Layer.Geom.geom_step;
  public static final Layer.Geom geom_text = Layer.Geom.geom_text;
  public static final Layer.Geom geom_tile = Layer.Geom.geom_tile;
  public static final Layer.Geom geom_violin = Layer.Geom.geom_violin;
  public static final Layer.Geom geom_vline = Layer.Geom.geom_vline;

  public static final Layer.Stat stat_bin = Layer.Stat.stat_bin;
  public static final Layer.Stat stat_bin2d = Layer.Stat.stat_bin2d;
  public static final Layer.Stat stat_bindot = Layer.Stat.stat_bindot;
  public static final Layer.Stat stat_binhex = Layer.Stat.stat_binhex;
  public static final Layer.Stat stat_boxplot = Layer.Stat.stat_boxplot;
  public static final Layer.Stat stat_contour = Layer.Stat.stat_contour;
  public static final Layer.Stat stat_density_2d = Layer.Stat.stat_density_2d;
  public static final Layer.Stat stat_density = Layer.Stat.stat_density;
  public static final Layer.Stat stat_ecdf = Layer.Stat.stat_ecdf;
  public static final Layer.Stat stat_ellipse = Layer.Stat.stat_ellipse;
  public static final Layer.Stat stat_function = Layer.Stat.stat_function;
  public static final Layer.Stat stat_identity = Layer.Stat.stat_identity;
  public static final Layer.Stat stat_qq = Layer.Stat.stat_qq;
  public static final Layer.Stat stat_quantile = Layer.Stat.stat_quantile;
  public static final Layer.Stat stat_smooth_methods = Layer.Stat.stat_smooth_methods;
  public static final Layer.Stat stat_smooth = Layer.Stat.stat_smooth;
  public static final Layer.Stat stat_spoke = Layer.Stat.stat_spoke;
  public static final Layer.Stat stat_sum = Layer.Stat.stat_sum;
  public static final Layer.Stat stat_summary_2d = Layer.Stat.stat_summary_2d;
  public static final Layer.Stat stat_summary_hex = Layer.Stat.stat_summary_hex;
  public static final Layer.Stat stat_summary = Layer.Stat.stat_summary;
  public static final Layer.Stat stat_unique = Layer.Stat.stat_unique;
  public static final Layer.Stat stat_vline = Layer.Stat.stat_vline;
  public static final Layer.Stat stat_ydensity = Layer.Stat.stat_ydensity;



  public void setDataFrame(DataFrame df) {
    myData = df;
  }

  public void setAesthetic(Aes aes) {
    myAes = aes;
  }

  /**
   * Theme option setters.
   */
  /**
   * Setting values for all lines in the theme
   * @return
   */
  public static ThemeLineOptionSetter line() {
    return new ThemeLineOptionSetter(Theme.KeyLine.values());
  }

  /**
   * Setting values for all rects in the theme
   * @return
   */
  public static ThemeRectOptionSetter rect() {
    return new ThemeRectOptionSetter(Theme.KeyRect.values());
  }

  /**
   * Setting values for all texts in the theme
   * @return
   */
  public static ThemeTextOptionSetter text() {
    return new ThemeTextOptionSetter(Theme.KeyText.values());
  }

  /**
   * Setting values for all title texts in the theme
   * @return
   */
  public static ThemeTextOptionSetter title() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_title_x,
        Theme.KeyText.axis_title_y,
        Theme.KeyText.legend_title,
        Theme.KeyText.plot_title);
  }

  public static ThemeTextOptionSetter axisTitle() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_title_x,
        Theme.KeyText.axis_title_y);
  }

  public static ThemeTextOptionSetter axisTitleX() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_title_x);
  }

  public static ThemeTextOptionSetter axisTitleY() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_title_y);
  }

  public static ThemeTextOptionSetter axisText() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_text_x,
        Theme.KeyText.axis_text_y);
  }

  public static ThemeTextOptionSetter axisTextX() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_text_x);
  }

  public static ThemeTextOptionSetter axisTextY() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.axis_text_y);
  }

  public static ThemeLineOptionSetter axisTicks() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_ticks_x,
        Theme.KeyLine.axis_ticks_y);
  }

  public static ThemeLineOptionSetter axisTicksX() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_ticks_x);
  }

  public static ThemeLineOptionSetter axisTicksY() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_ticks_y);
  }

  public static ThemeUnitOptionSetter axisTicksLength() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.axis_ticks_length);
  }
  
  public static ThemeUnitOptionSetter axisTicksMargin() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.axis_ticks_margin);
  }
  
  public static ThemeLineOptionSetter axisLine() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_line_x,
        Theme.KeyLine.axis_line_y);
  }
  
  public static ThemeLineOptionSetter axisLineX() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_line_x);
  }
  
  public static ThemeLineOptionSetter axisLineY() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.axis_line_y);
  }
  
  public static ThemeRectOptionSetter legendBackground() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.legend_background);
  }
  
  public static ThemeUnitOptionSetter legendMargin() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.legend_margin);
  }
  
  public static ThemeRectOptionSetter legendKey() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.legend_key);
  }
  
  public static ThemeUnitOptionSetter legendKeySize() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.legend_key_width,
        Theme.KeyUnit.legend_key_height);
  }
  
  public static ThemeUnitOptionSetter legendKeyHeight() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.legend_key_height);
  }
  
  public static ThemeUnitOptionSetter legendKeyWidth() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.legend_key_width);
  }
  
  public static ThemeTextOptionSetter legendText() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.legend_text);
  }
  
  public static ThemeZeroOneOptionSetter legendTextAlign() {
    return new ThemeZeroOneOptionSetter(
        Theme.KeyZeroOne.legend_text_align);
  }
  
  public static ThemeTextOptionSetter legendTitle() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.legend_title);
  }
  
  public static ThemeZeroOneOptionSetter legendTitleAlign() {
    return new ThemeZeroOneOptionSetter(
        Theme.KeyZeroOne.legend_title_align);
  }
  
  public static ThemePositionOptionSetter legendPosition() {
    return new ThemePositionOptionSetter(
        Theme.KeyPosition.legend_position);
  }
  
  public static ThemeDirectionOptionSetter legendDirection() {
    return new ThemeDirectionOptionSetter(
        Theme.KeyDirection.legend_direction);
  }
  
  public static ThemeJustificationOptionSetter legendJustification() {
    return new ThemeJustificationOptionSetter(
        Theme.KeyJustification.legend_justification);
  }
  
  public static ThemeDirectionOptionSetter legendBox() {
    return new ThemeDirectionOptionSetter(
        Theme.KeyDirection.legend_box);
  }
  
  public static ThemePositionOptionSetter legendBoxJust() {
    return new ThemePositionOptionSetter(
        Theme.KeyPosition.legend_box_just);
  }
  
  public static ThemeRectOptionSetter panelBackground() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.panel_background);
  }
  
  public static ThemeRectOptionSetter panelBorder() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.panel_border);
  }
  
  public static ThemeUnitOptionSetter panelMargin() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.panel_margin);
  }
  
  public static ThemeLineOptionSetter panelGrid() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_major_x,
        Theme.KeyLine.panel_grid_major_y,
        Theme.KeyLine.panel_grid_minor_x,
        Theme.KeyLine.panel_grid_minor_y);
  }
  
  public static ThemeLineOptionSetter panelGridMajor() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_major_x,
        Theme.KeyLine.panel_grid_major_y);
  }
  
  public static ThemeLineOptionSetter panelGridMinor() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_major_x,
        Theme.KeyLine.panel_grid_major_y);
  }
  
  public static ThemeLineOptionSetter panelGridMajorX() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_major_x);
  }
  
  public static ThemeLineOptionSetter panelGridMajorY() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_major_y);
  }
  
  public static ThemeLineOptionSetter panelGridMinorX() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_minor_x);
  }
  
  public static ThemeLineOptionSetter panelGridMinorY() {
    return new ThemeLineOptionSetter(
        Theme.KeyLine.panel_grid_minor_y);
  }
  
  public static ThemeRectOptionSetter plotBackground() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.plot_background);
  }
  
  public static ThemeTextOptionSetter plotTitle() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.plot_title);
  }
  
  public static ThemeUnitOptionSetter plotMargin() {
    return new ThemeUnitOptionSetter(
        Theme.KeyUnit.plot_margin);
  }
  
  public static ThemeRectOptionSetter stripBackground() {
    return new ThemeRectOptionSetter(
        Theme.KeyRect.strip_background);
  }
  
  public static ThemeTextOptionSetter stripText() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.strip_text_x,
        Theme.KeyText.strip_text_y);
  }
  
  public static ThemeTextOptionSetter stripTextX() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.strip_text_x);
  }
  
  public static ThemeTextOptionSetter stripTextY() {
    return new ThemeTextOptionSetter(
        Theme.KeyText.strip_text_y);
  }
}

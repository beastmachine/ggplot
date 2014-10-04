package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.google.common.base.Preconditions;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.geom.Geometry;
import org.beastmachine.ggplot.stat.StatIdentity;
import org.beastmachine.ggplot.stat.Statistic;

public class Layer {
  private DataFrame myData;
  private DataFrame plotData;
  private Aes myAes;
  private Statistic myStat;
  private Geometry myGeom;

  public Layer(DataFrame data, Aes aes, Geometry geom){
    myStat = new StatIdentity();
    myData = data;
    myAes = aes;
    myGeom = geom;
  }

  public void createPlotData() {
    plotData = new DataFrame();
    for(Aes.Aesthetic aes: myAes.getSetAesthetics()){
      String val = myAes.getVariable(aes);
      plotData.c(aes.toString(), myData.get(val));
    }
    //TODO do stuff that isn't just the defaultData

  }

  public enum Geom {
    geom_abline, geom_area, geom_bar, geom_bin2d, geom_blank, geom_boxplot, geom_contour,
    geom_crossbar, geom_density, geom_density2d, geom_dotplot, geom_errorbar,
    geom_errorbarh, geom_freqpoly, geom_hex, geom_histogram, geom_hline, geom_jitter,
    geom_line, geom_linerange, geom_map, geom_path, geom_point, geom_pointrange, 
    geom_polygon, geom_quantile, geom_raster, geom_rect, geom_ribbon, geom_rug,
    geom_segment, geom_smooth, geom_step, geom_text, geom_tile, geom_violin, geom_vline;

  }

  public enum Stat {
    stat_bin, stat_bin2d, stat_bindot, stat_binhex, stat_boxplot, stat_contour,
    stat_density_2d, stat_density, stat_ecdf, stat_ellipse, stat_function,
    stat_identity, stat_qq, stat_quantile, stat_smooth_methods, stat_smooth,
    stat_spoke, stat_sum, stat_summary_2d, stat_summary_hex, stat_summary,
    stat_unique, stat_vline, stat_ydensity
  }

  public double minX() {
    return plotData.get(myAes.getVariable(Aes.Aesthetic.x)).min();
  }

  public double maxX() {
    return plotData.get(myAes.getVariable(Aes.Aesthetic.x)).max();
  }

  public double minY() {
    return plotData.get(myAes.getVariable(Aes.Aesthetic.y)).min();
  }

  public double maxY() {
    return plotData.get(myAes.getVariable(Aes.Aesthetic.y)).max();
  }

  public void draw(GGPlot.Scalable scale, Graphics2D g) {
    if(plotData == null){
      createPlotData();
    }
    myGeom.draw(plotData,myAes, scale, g);
  }

  public void setDataFrame(DataFrame df) {
    myData = df;
  }

  public void setAesthetic(Aes aes) {
    myAes = aes;
  }

}

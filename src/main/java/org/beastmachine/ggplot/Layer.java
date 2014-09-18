package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.google.common.base.Preconditions;
import org.beastmachine.dataframe.DataFrame;

public class Layer {
  private DataFrame myData;
  private DataFrame plotData;
  private Aes myAes;
  private Stat myStat;
  private Geom myGeom;
  
  public Layer(DataFrame data, Aes aes, Layer.Geom geom){
  	myStat = new StatIdentity();
  	myData = data;
  	myAes = aes;
  	myGeom = geom;
  	
  }
  
  public void paint2D(Graphics2D g, Dimension pixels, Dimension points) {
    // myGeom.paint2D(g, pixels, points);
  }

  protected void createPlotData() {
    // TODO
  }

  public enum Geom {
  	geom_abline, geom_area, geom_bar, geom_bin2d, geom_blank, geom_boxplot, geom_contour,
  	geom_crossbar, geom_density, geom_density2d, geom_dotplot, geom_errorbar,
  	geom_errorbarh, geom_freqpoly, geom_hex, geom_histogram, geom_hline, geom_jitter,
  	geom_line, geom_linerange, geom_map, geom_path, geom_point, geom_pointrange, 
  	geom_polygon, geom_quantile, geom_raster, geom_rect, geom_ribbon, geom_rug,
  	geom_segment, geom_smooth, geom_step, geom_text, geom_tile, geom_violin, geom_vline
  }

//  public enum Stat {
//    stat_bin, stat_bin2d, stat_bindot, stat_binhex, stat_boxplot, stat_contour,
//    stat_density_2d, stat_density, stat_ecdf, stat_ellipse, stat_function,
//    stat_identity, stat_qq, stat_quantile, stat_smooth_methods, stat_smooth,
//    stat_spoke, stat_sum, stat_summary_2d, stat_summary_hex, stat_summary,
//    stat_unique, stat_vline, stat_ydensity
//  }
  
}

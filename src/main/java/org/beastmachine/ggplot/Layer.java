package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.google.common.base.Preconditions;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.Statistic;

public class Layer {
  private DataFrame myData;
  private DataFrame plotData;
  private Aes myAes;
  private Statistic myStat;
  private Geom myGeom;
  private Geometry myGeomImpl;
  
  public Layer(DataFrame data, Aes aes, Layer.Geom geom){
  	myStat = new StatIdentity();
  	myData = data;
  	myAes = aes;
  	myGeom = geom;
  	switch(myGeom){
  	case geom_point:
  		myGeomImpl = new GeometryPoint();
  		break;
  	default:
  		Preconditions.checkState(false, "geom not yet supported");
  		break;
  	}
  }

  public void createPlotData(DataFrame defaultData) {
  	plotData = new DataFrame();
  	for(Column c: defaultData.columns()){
  		plotData.c(c);
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

	public void draw(Scalable scale, Graphics2D g) {
	  if(plotData == null){
	  	createPlotData(myData);
	  }
	  myGeomImpl.draw(plotData,myAes, scale, g);
  }
  
}

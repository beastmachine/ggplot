package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.visual.Paintable;

public class GGPlot implements Paintable{

	private Scale myScale;
	private Coord myCoord;
	private Facet myFacet;
	private List<Layer> myLayers;
	private Defaults myDefaults;
	private Aes myAes;
	private DataFrame myData;
	


	public GGPlot(DataFrame df, Aes aes) {
		myData = df;
		myAes = aes;
		myDefaults = Defaults.getPrettyDefaults();
		myLayers = new ArrayList<Layer>();
		myCoord = new CoordCartesian();
		myScale = new Scale(df, myAes, myCoord, myLayers);
		myFacet = new FacetNone(myDefaults, myScale, myCoord, myLayers);
	}
	
	public static GGPlot ggplot(){
		return new GGPlot(new DataFrame(), new Aes());
	}
	
	public static GGPlot ggplot(DataFrame df){
		return new GGPlot(df, new Aes());
	}
	
	public static GGPlot ggplot(DataFrame df, Aes aes){
		return new GGPlot(df, aes);
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
		double pixelsPerPointHeight = (double)pixels.getHeight() / (double)points.getHeight();
		double pixelsPerPointWidth = (double)pixels.getWidth() / (double)points.getWidth();
		
		int bottomMarginPixels = (int)Math.round(myDefaults.getBottumMarginPoints() * pixelsPerPointHeight);
		int topMarginPixels = (int)Math.round(myDefaults.getTopMarginPoints() * pixelsPerPointHeight);
		int leftMarginPixels = (int)Math.round(myDefaults.getLeftMarginPoints() * pixelsPerPointWidth);
		int rightMarginPixels = (int)Math.round(myDefaults.getRightMarginPoints() * pixelsPerPointWidth);

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


}

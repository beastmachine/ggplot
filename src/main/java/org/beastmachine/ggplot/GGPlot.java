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
	
	public GGPlot geom_point(){
		addLayer(new Layer(myData, myAes, geom_point));
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
		//not sure what to do
		return this;
	}
	
	public GGPlot ggsave(String file){
		try {
	    PaintPDF.paintToPDF(this, new Dimension(621,480),
	        new Dimension(792,612), file);
    } catch (IOException e) {
	    e.printStackTrace();
    }
		return this;
	}
	
//	public GGPlot layer(Geom geom, Stat stat, )

	public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
		double pixelsPerPointHeight = pixels.getHeight() / points.getHeight();
		double pixelsPerPointWidth = pixels.getWidth() / points.getWidth();

		int bottomMarginPixels = (int)Math.round(myDefaults.getBottumMarginPoints() * pixelsPerPointHeight);
		int topMarginPixels = (int)Math.round(myDefaults.getTopMarginPoints() * pixelsPerPointHeight);
		int leftMarginPixels = (int)Math.round(myDefaults.getLeftMarginPoints() * pixelsPerPointWidth);
		int rightMarginPixels = (int)Math.round(myDefaults.getRightMarginPoints() * pixelsPerPointWidth);

		System.out.println("margins "+bottomMarginPixels+" "+topMarginPixels+" "+leftMarginPixels+" "+rightMarginPixels);
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
	
	public static final Aes.Aesthetic adj = Aes.Aesthetic.adj;
	public static final Aes.Aesthetic alpha = Aes.Aesthetic.alpha;
	public static final Aes.Aesthetic angle = Aes.Aesthetic.angle;
	public static final Aes.Aesthetic bg = Aes.Aesthetic.bg;
	public static final Aes.Aesthetic cex = Aes.Aesthetic.cex;
	public static final Aes.Aesthetic col = Aes.Aesthetic.col;
	public static final Aes.Aesthetic color = Aes.Aesthetic.color;
	public static final Aes.Aesthetic colour = Aes.Aesthetic.colour;
	public static final Aes.Aesthetic fg = Aes.Aesthetic.fg;
	public static final Aes.Aesthetic fill = Aes.Aesthetic.fill;
	public static final Aes.Aesthetic group = Aes.Aesthetic.group;
	public static final Aes.Aesthetic hjust = Aes.Aesthetic.hjust;
	public static final Aes.Aesthetic label = Aes.Aesthetic.label;
	public static final Aes.Aesthetic linetype = Aes.Aesthetic.linetype;
	public static final Aes.Aesthetic lower = Aes.Aesthetic.lower;
	public static final Aes.Aesthetic lty = Aes.Aesthetic.lty;
	public static final Aes.Aesthetic lwd = Aes.Aesthetic.lwd;
	public static final Aes.Aesthetic max = Aes.Aesthetic.max;
	public static final Aes.Aesthetic middle = Aes.Aesthetic.middle;
	public static final Aes.Aesthetic min = Aes.Aesthetic.min;
	public static final Aes.Aesthetic order = Aes.Aesthetic.order;
	public static final Aes.Aesthetic pch = Aes.Aesthetic.pch;
	public static final Aes.Aesthetic radius = Aes.Aesthetic.radius;
	public static final Aes.Aesthetic sample = Aes.Aesthetic.sample;
	public static final Aes.Aesthetic shape = Aes.Aesthetic.shape;
	public static final Aes.Aesthetic size = Aes.Aesthetic.size;
	public static final Aes.Aesthetic srt = Aes.Aesthetic.srt;
	public static final Aes.Aesthetic upper = Aes.Aesthetic.upper;
	public static final Aes.Aesthetic vjust = Aes.Aesthetic.vjust;
	public static final Aes.Aesthetic weight = Aes.Aesthetic.weight;
	public static final Aes.Aesthetic x = Aes.Aesthetic.x;
	public static final Aes.Aesthetic xend = Aes.Aesthetic.xend;
	public static final Aes.Aesthetic xintercept = Aes.Aesthetic.xintercept;
	public static final Aes.Aesthetic xmax = Aes.Aesthetic.xmax;
	public static final Aes.Aesthetic xmin = Aes.Aesthetic.xmin;
	public static final Aes.Aesthetic y = Aes.Aesthetic.y;
	public static final Aes.Aesthetic yend = Aes.Aesthetic.yend;
	public static final Aes.Aesthetic yintercept = Aes.Aesthetic.yintercept;
	public static final Aes.Aesthetic ymax = Aes.Aesthetic.ymax;
	public static final Aes.Aesthetic ymin = Aes.Aesthetic.ymin;
	public static final Aes.Aesthetic z = Aes.Aesthetic.z;

	public static Aes aes(Aes.Aesthetic key, String val){
		Aes aes = new Aes();
		aes.a(key, val);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		return aes;
	}
	
	/**
	 * I suffer now so that others will not have to later
	 * 
	 * @param key1
	 * @param val1
	 * @param key2
	 * @param val2
	 * @param key3
	 * @param val3
	 * @param key4
	 * @param val4
	 * @param key5
	 * @param val5
	 * @param key6
	 * @param val6
	 * @param key7
	 * @param val7
	 * @param key8
	 * @param val8
	 * @param key9
	 * @param val9
	 * @param key10
	 * @param val10
	 * @param key11
	 * @param val11
	 * @return
	 */
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10,
			Aes.Aesthetic key11, String val11){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		aes.a(key11, val11);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10,
			Aes.Aesthetic key11, String val11,
			Aes.Aesthetic key12, String val12){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		aes.a(key11, val11);
		aes.a(key12, val12);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10,
			Aes.Aesthetic key11, String val11,
			Aes.Aesthetic key12, String val12,
			Aes.Aesthetic key13, String val13){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		aes.a(key11, val11);
		aes.a(key12, val12);
		aes.a(key13, val13);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10,
			Aes.Aesthetic key11, String val11,
			Aes.Aesthetic key12, String val12,
			Aes.Aesthetic key13, String val13,
			Aes.Aesthetic key14, String val14){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		aes.a(key11, val11);
		aes.a(key12, val12);
		aes.a(key13, val13);
		aes.a(key14, val14);
		return aes;
	}
	
	public static Aes aes(Aes.Aesthetic key1, String val1, 
			Aes.Aesthetic key2, String val2,
			Aes.Aesthetic key3, String val3,
			Aes.Aesthetic key4, String val4,
			Aes.Aesthetic key5, String val5,
			Aes.Aesthetic key6, String val6,
			Aes.Aesthetic key7, String val7,
			Aes.Aesthetic key8, String val8,
			Aes.Aesthetic key9, String val9,
			Aes.Aesthetic key10, String val10,
			Aes.Aesthetic key11, String val11,
			Aes.Aesthetic key12, String val12,
			Aes.Aesthetic key13, String val13,
			Aes.Aesthetic key14, String val14,
			Aes.Aesthetic key15, String val15){
		Aes aes = new Aes();
		aes.a(key1, val1);
		aes.a(key2, val2);
		aes.a(key3, val3);
		aes.a(key4, val4);
		aes.a(key5, val5);
		aes.a(key6, val6);
		aes.a(key7, val7);
		aes.a(key8, val8);
		aes.a(key9, val9);
		aes.a(key10, val10);
		aes.a(key11, val11);
		aes.a(key12, val12);
		aes.a(key13, val13);
		aes.a(key14, val14);
		aes.a(key15, val15);
		return aes;
	}

}

package org.beastmachine.ggplot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.visual.Colors;
import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.ggplot.visual.Paintable;
import org.beastmachine.util.NumberFormatter;

import com.google.common.base.Preconditions;

import gnu.trove.list.array.TDoubleArrayList;

public class Scale implements Paintable {

	private Transformer xTransform;
	private Transformer yTransform;
	private Coord myCoord;
	private Aes aesthetic;
	private DataFrame data;
	private DataFrame plotData;
	private List<Layer> layers;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private ArrayList<String> xBreaks;
	private ArrayList<String> yBreaks;
	private double xRange;
	private double minXData;
	private double maxXData;
	private double minYData;
	private double maxYData;
	private double minXDataOnPlot;
	private double maxXDataOnPlot;

	public Scale(DataFrame data, Aes aesthetic, Coord myCoord, List<Layer> layers) {
	 this.myCoord = myCoord;
	 this.aesthetic = aesthetic;
	 this.data = data;
	 this.layers = layers;
	 xTransform = new IdentityTransform();
	 yTransform = new IdentityTransform();
	 
  }
	
	@Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {

		Graphics2DState state = new Graphics2DState(g);

		
		
		
		if(plotData == null){
			createPlotData(pixels, points);
		}
		System.out.println("pixel range to work in for scale "+minX+" "+maxX+" "+minY+" "+maxY);
		int xGuideHeight = getXGuideHeight(g);
		int yGuideWidth = getYGuideWidth(g);
		
	  double pixelsY = (maxY-minY-xGuideHeight);
	  double pixelsX = (maxX-minX-yGuideWidth);
	  double pixelsDiag = Math.sqrt(Math.pow(pixelsY,2)+Math.pow(pixelsX, 2));
	  double pixelsDataDiag = pixelsDiag - 0.5*72.0;//TODO pull this from theme .75 inches diag to start of data and 72 dpi
	  System.out.println("pixels diag "+pixelsDiag+" pixelsDataDiag "+pixelsDataDiag);
	  double aspectRatio = pixelsX/pixelsY;
	  
	  double pixelsSubrangeX = Math.sqrt(Math.pow(pixelsDataDiag,2)/((Math.pow(aspectRatio, 2)+1)/Math.pow(aspectRatio, 2)));
	  double pixelsSubrangeY = 1/aspectRatio*pixelsSubrangeX;

	  minXDataOnPlot = minXData - (pixelsX-pixelsSubrangeX)/pixelsX *(maxXData-minXData)/2.0;
	  maxXDataOnPlot = maxXData + (pixelsX-pixelsSubrangeX)/pixelsX *(maxXData-minXData)/2.0;
	  System.out.println("pixels X "+pixelsX+" pixelsSubrangeX "+pixelsSubrangeX);
	  System.out.println("minXDataOnPlot "+minXDataOnPlot);
	  System.out.println("maxXDataOnPlot "+maxXDataOnPlot);
				
		drawXGuide(g, yGuideWidth);
		System.out.println("xguideheight "+xGuideHeight);
	  
	  g.setColor(Colors.grey87);
	  g.fillRect(minX+yGuideWidth , minY, maxX-minX-yGuideWidth, maxY-minY-xGuideHeight);
		
	  g.setColor(Color.red);
	  System.out.println("red "+((int)((pixelsX-pixelsSubrangeX)/2.0))+" "+(int)((pixelsY-pixelsSubrangeY)/2.0));
	  g.drawRect((int)((pixelsX-pixelsSubrangeX)/2.0)+minX+yGuideWidth, minY+(int)((pixelsY-pixelsSubrangeY)/2.0), (int)pixelsSubrangeX, (int)pixelsSubrangeY);
		
	  state.restore(g);
  }

	private int getYGuideWidth(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 4)); //TODO pull this from somewhere
		int yTickLength = 2; //TODO pull this from somewhere
		double stringHeight = g.getFontMetrics().getStringBounds("exampletext", g).getHeight();
	  return (int)Math.round(stringHeight + yTickLength);
  }

	private int getXGuideHeight(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 4)); //TODO pull this from somewhere
		int xTickLength = 2; //TODO pull this from somewhere
		double stringHeight = g.getFontMetrics().getStringBounds("exampletext", g).getHeight();
		 return (int)Math.round(stringHeight + xTickLength);
  }

	private int drawXGuide(Graphics2D g, int yGuideWidth) {
		Color xguideColor = Colors.grey42;// TODO pull this from somewhere
		Color xTickColor = Colors.black; //TODO pull this from somewhere
		BasicStroke xTickStroke = new BasicStroke(0.33f); //TODO pull this from somewhere
		int xTickLength = 2; //TODO pull this from somewhere
		System.out.println("xlabY "+maxY);
		g.setFont(new Font("Arial", Font.PLAIN, 4)); //TODO pull this from somewhere
		double stringHeight = g.getFontMetrics().getStringBounds("exampletext", g).getHeight();
		int xlabY = (int) Math.round(maxY);
	  for(String s: xBreaks){
	  	double stringWidth = g.getFontMetrics().getStringBounds(s, g).getWidth();
	  	System.out.println("string width of "+s+" "+stringWidth);
	  	int xguideX = minX + yGuideWidth + (int)(Math.round(minXDataOnPlot+ (Double.parseDouble(s)-minXDataOnPlot)/
	  			(maxXDataOnPlot-minXDataOnPlot)*(maxX-(minX+yGuideWidth)) - (stringWidth/2.0)));
	  	System.out.println("attempting to draw string "+s+" at "+xguideX+", "+xlabY+" and this string is of width "+stringWidth+" with "+s.length()+" chars");
	  	g.setColor(xguideColor);
	  	g.drawString(s, xguideX, xlabY);
	  	g.setColor(xTickColor);
	  	g.setStroke(xTickStroke);
	  	int xtickX = minX + yGuideWidth + (int)(Math.round(minXDataOnPlot + (Double.parseDouble(s)-minXDataOnPlot)/
	  			(maxXDataOnPlot-minXDataOnPlot)*(maxX-(minX+yGuideWidth))));
	  	g.drawLine(xtickX, (int)(xlabY-stringHeight), xtickX, (int)(xlabY-stringHeight-xTickLength));
	  }
	  return (int)(stringHeight+xTickLength);
  }

	private void createPlotData(Dimension2D pixels, Dimension2D points) {
	  plotData = new DataFrame();
	  for(Aes.Aesthetic aes: aesthetic.getSetAesthetics()){
	  	String val = aesthetic.getVariable(aes);
	  	if(data.hasColumn(val)){
	  		if(aes == Aes.Aesthetic.x){
	  			plotData.c(aes.toString(), xTransform.transform(data.get(val)));
	  		}
	  		else if(aes == Aes.Aesthetic.y){
	  			plotData.c(aes.toString(), yTransform.transform(data.get(val)));
	  		}
	  		else{
	  			Preconditions.checkState(false, "Aesthetic ",aes," not currently supported. TODO"); //TODO
	  			plotData.c(aes.toString(), data.get(val));
	  		}
	  	}
	  }

    double xmin = Double.MAX_VALUE;
    double xmax = Double.NEGATIVE_INFINITY;

    double ymin = Double.MAX_VALUE;
    double ymax = Double.NEGATIVE_INFINITY;
	  Preconditions.checkState(layers.size()>0," must have at least one layer");
	  for (Layer l: layers) {
      l.createPlotData(plotData); // create layer plot data given default plot data which might be empty
      xmin = Math.min(xmin, l.minX());
      xmax = Math.max(xmax, l.maxX());
      
      ymin = Math.min(ymin, l.minY());
      ymax = Math.max(ymax, l.maxY());
	  }

	  System.out.println("minmax "+xmin+" "+xmax+" "+ymin+" "+ymax);
	  xBreaks = calcAxisBreaksAndLimits(xmin, xmax);
	  yBreaks = calcAxisBreaksAndLimits(ymin, ymax);
	  minXData = xmin;
	  maxXData = xmax;
	  minYData = ymin;
	  maxYData = ymax;
	  

  }

	public void setXTransform(Transformer trans){
		xTransform = trans;
	}
	
	public void setYTransform(Transformer trans){
		yTransform = trans;
	}


	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval){
		double diff = maxval - minval;
		double base10 = Math.log10(diff);
		double power = Math.floor(base10);
		double base_unit = Math.pow(10, power);
		double step = base_unit / 2;
		return calcAxisBreaksAndLimits(minval,maxval, step);
	}

	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, int nlabs){
		double diff = maxval - minval;
		double tick_range = diff / (double)nlabs;
		// make the tick range nice looking...
		double power = Math.ceil(Math.log10(tick_range));
		double step = Math.round(tick_range / Math.pow(10,power)) * Math.pow(10, power);
		return calcAxisBreaksAndLimits(minval, maxval, step);
	}

	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, double step){
		/**
		 * 
		 * Calculates axis breaks and suggested limits.
		 * The limits are computed as minval/maxval -/+ 1/3 step of ticks.
		 * Parameters
		 * ----------
		 * minval : number
		 *   lowest value on this axis
		 * maxval : number
		 *   higest number on this axis
		 * nlabs : int
		 *   number of labels which should be displayed on the axis
		 * Default: None
		 **/
		ArrayList<String> toReturn = new ArrayList<String>();
		TDoubleArrayList steps = drange(minval-(step/3), maxval+(step/3), step);
//		xRange = maxval+(step/3)-(minval-(step/3));
		System.out.println("xrange in data for plot "+xRange);
		for(int ii = 0; ii < steps.size(); ii++){
			toReturn.add(NumberFormatter.format(steps.get(ii)));
		}
		return toReturn;
	}

	private TDoubleArrayList drange(double start, double stop, double step){
		/**
		 * Compute the steps in between start and stop
		 * Only steps which are a multiple of `step` are used.
		 */
		TDoubleArrayList toReturn = new TDoubleArrayList();
		double  r = (Math.floor(start / step) * step) + step; // # the first step higher than start
		//# all subsequent steps are multiple of "step"!
		while(r < stop){
			toReturn.add(r);
			r += step;
		}
		return toReturn;
	}
	
	public interface Transformer{
		public Column transform(Column c);
	}
	
	public class IdentityTransform implements Transformer {
	  @Override
	  public Column transform(Column c) {
		  return c; // no deep copy for memory considerations, but could be dangerous
	  }
  }



	public void setArea(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
  }

	public void addLayer(Layer layer) {
	  layers.add(layer);
  }


}


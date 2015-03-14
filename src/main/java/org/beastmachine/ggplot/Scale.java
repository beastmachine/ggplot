package org.beastmachine.ggplot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.coord.Coord;
import org.beastmachine.ggplot.theme.TextFormat;
import org.beastmachine.ggplot.theme.Theme;
import org.beastmachine.ggplot.theme.Unit;
import org.beastmachine.ggplot.visual.Colors;
import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.ggplot.visual.Paintable;
import org.beastmachine.util.GDimension2D;
import org.beastmachine.util.NumberFormatter;
import org.beastmachine.util.Text;

import com.google.common.base.Preconditions;

import gnu.trove.list.array.TDoubleArrayList;




public class Scale implements Paintable, GGPlot.Scalable{

  private Transformer xTransform;
  private Transformer yTransform;
  private Coord myCoord;
  //	private Aes aesthetic;
  //	private DataFrame data;
  //	private DataFrame plotData;
  private List<Layer> layers;
  private ArrayList<DataGuide> xBreaks;
  private ArrayList<DataGuide> yBreaks;
  private double xRange;
  private double maxX;
  private double maxY;
  private double minXData;
  private double maxXData;
  private double minYData;
  private double maxYData;
  private double minXDataOnPlot;
  private double maxXDataOnPlot;
  private StepHolder xStep;
  private StepHolder yStep;
  private Integer xGuideHeight;
  private Integer yGuideWidth;
  private double maxYDataOnPlot;
  private double minYDataOnPlot;
  private Font g;
  private boolean plotDataCreated;
  private Theme theme;

  public Scale(Coord myCoord, List<Layer> layers, Theme theme) {
    this.myCoord = myCoord;
    this.layers = layers;
    xTransform = new IdentityTransform();
    yTransform = new IdentityTransform();
    this.theme = theme;

  }

  @Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
    maxX = pixels.getWidth();
    maxY = pixels.getHeight();
    
    double pixelsPerPoint = pixels.getWidth()/points.getWidth();
    Graphics2DState state = new Graphics2DState(g);

    if(!plotDataCreated){
      createPlotData(pixels, points);
    }

    //    System.out.println("pixel range to work in for scale "+minX+" "+maxX+" "+minY+" "+maxY);
    xGuideHeight = getXGuideHeight(g, pixelsPerPoint);
    yGuideWidth = getYGuideWidth(g,pixelsPerPoint);
    System.out.println("xguideheight "+xGuideHeight+" yguidewidth "+yGuideWidth);
    double pixelsY = (pixels.getHeight()-xGuideHeight);
    double pixelsX = (pixels.getWidth()-yGuideWidth);
    double pixelsDiag = Math.sqrt(Math.pow(pixelsY,2)+Math.pow(pixelsX, 2));
    double pixelsDataDiag = pixelsDiag - 0.5*72.0;//TODO pull this from theme .75 inches diag to start of data and 72 dpi
    System.out.println("pixels diag "+pixelsDiag+" pixelsDataDiag "+pixelsDataDiag);
    double aspectRatio = pixelsX/pixelsY;

    double pixelsSubrangeX = Math.sqrt(Math.pow(pixelsDataDiag,2)/((Math.pow(aspectRatio, 2)+1)/Math.pow(aspectRatio, 2)));
    double pixelsSubrangeY = 1/aspectRatio*pixelsSubrangeX;

    minXDataOnPlot = minXData - (pixelsX-pixelsSubrangeX)/pixelsX *(maxXData-minXData)/2.0;
    maxXDataOnPlot = maxXData + (pixelsX-pixelsSubrangeX)/pixelsX *(maxXData-minXData)/2.0;
    minYDataOnPlot = minYData - (pixelsY-pixelsSubrangeY)/pixelsY *(maxYData-minYData)/2.0;
    maxYDataOnPlot = maxYData + (pixelsY-pixelsSubrangeY)/pixelsY *(maxYData-minYData)/2.0;
    System.out.println("pixels X "+pixelsX+" pixelsSubrangeX "+pixelsSubrangeX);
    System.out.println("minXDataOnPlot "+minXDataOnPlot);
    System.out.println("maxXDataOnPlot "+maxXDataOnPlot);

    g.setColor(Colors.grey87);
    //	  g.drawRect(minX+yGuideWidth , minY, maxX-minX-yGuideWidth, maxY-minY-xGuideHeight);
    //    System.out.println("first attempt is "+(minX+yGuideWidth)+" "+(minY)+" "+(maxX-minX-yGuideWidth)+" "+(maxY-minY-xGuideHeight));

    //	  g.setColor(Color.red); //TODO remove this, just a guide
    //	  g.drawRect((int)((pixelsX-pixelsSubrangeX)/2.0)+minX+yGuideWidth, minY+(int)((pixelsY-pixelsSubrangeY)/2.0), 
    //	  		(int)Math.round(pixelsSubrangeX), (int)Math.round(pixelsSubrangeY));
    g.fillRect((int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)), (int)Math.round(yDataPointToPixelLocation(maxYDataOnPlot)), 
        (int)Math.round(xDataPointToPixelLocation(maxXDataOnPlot)-xDataPointToPixelLocation(minXDataOnPlot)), 
        (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)-yDataPointToPixelLocation(maxYDataOnPlot)));
    System.out.println("second attempt is "+(xDataPointToPixelLocation(minXDataOnPlot))+" "+(yDataPointToPixelLocation(minYDataOnPlot))+" "+
        (xDataPointToPixelLocation(maxXDataOnPlot)-xDataPointToPixelLocation(minXDataOnPlot))+" "+(yDataPointToPixelLocation(maxYDataOnPlot)-yDataPointToPixelLocation(minYDataOnPlot)));

    drawXGuide(g);
    drawYGuide(g);
    System.out.println("xguideheight "+xGuideHeight);

    //    for(Layer l: layers){
    //      l.draw(this, g);
    //    }


    state.restore(g);
  }

  private int getYGuideWidth(Graphics2D g, double pixelsPerPoint) {
    if(yGuideWidth != null)
      return yGuideWidth.intValue();
    double tickLength = theme.get(Theme.KeyUnit.axis_ticks_length).getPixels(pixelsPerPoint);
    double textHeight = theme.get(Theme.KeyText.axis_text_y).getLineHeight(g, pixelsPerPoint);
    double margin = theme.get(Theme.KeyUnit.axis_ticks_margin).getPixels(pixelsPerPoint);
    yGuideWidth = (int)Math.round(tickLength + textHeight + margin);
    return yGuideWidth.intValue();
  }

  private int getXGuideHeight(Graphics2D g, double pixelsPerPoint) {
    if(xGuideHeight != null)
      return xGuideHeight.intValue();
    double tickLength = theme.get(Theme.KeyUnit.axis_ticks_length).getPixels(pixelsPerPoint);
    double textHeight = theme.get(Theme.KeyText.axis_text_x).getLineHeight(g, pixelsPerPoint);
    double margin = theme.get(Theme.KeyUnit.axis_ticks_margin).getPixels(pixelsPerPoint);
    xGuideHeight = (int)Math.round(tickLength + textHeight + margin);
    return xGuideHeight.intValue();
  }

  public void drawXTicksAndText(Graphics2D g, Dimension2D pixels, Dimension2D points){
    double pixelsPerPoint = pixels.getWidth()/points.getWidth();
    if(!plotDataCreated){
      createPlotData(pixels, points);
      getYGuideWidth(g, pixelsPerPoint);
      getXGuideHeight(g, pixelsPerPoint);
    }
    TextFormat xText = theme.get(Theme.KeyText.axis_text_x);
    for(int ii = 0; ii < xBreaks.size(); ii++){
      String content = xBreaks.get(ii).dataLabel;
      double xbreak = xBreaks.get(ii).dataBreak;
      double stringWidth = g.getFontMetrics(xText.getFont(pixelsPerPoint)).getStringBounds(content, g).getWidth();
      Text xBreakText = new Text(content, xText);
      double stringHeight = xText.getLineHeight(g, pixelsPerPoint);
      double xguideX = xDataPointToPixelLocation(xbreak) - (int)(stringWidth/2.0);

      double xtickX = xDataPointToPixelLocation(xbreak);
      double xtickY = pixels.getHeight() - 
          theme.get(Theme.KeyText.axis_text_x).getLineHeight(g, pixelsPerPoint) -
          theme.get(Theme.KeyUnit.axis_ticks_margin).getPixels(pixelsPerPoint);
      double tickLength = theme.get(Theme.KeyUnit.axis_ticks_length).getPixels(pixelsPerPoint);
      g.drawLine((int)Math.round(xtickX), (int)Math.round(xtickY), (int)Math.round(xtickX), (int)Math.round(xtickY-tickLength));
      System.out.println("drawing tick at "+xtickX);

      g.translate(xguideX, xtickY);
      xBreakText.paint2D(g, new GDimension2D(stringWidth, stringHeight), 
          new GDimension2D(stringWidth/pixelsPerPoint, stringHeight/pixelsPerPoint));
      g.translate(-xguideX, -xtickY);

    }
  }

  private void drawXGuide(Graphics2D g) {
    Color xguideColor = Colors.grey42;// TODO pull this from somewhere
    Color xTickColor = Colors.black; //TODO pull this from somewhere
    BasicStroke xMajorTickStroke = new BasicStroke(0.4f); //TODO pull this from somewhere
    BasicStroke xMinorTickStroke = new BasicStroke(0.2f);
    int xTickLength = 2; //TODO pull this from somewhere
    g.setFont(new Font("Arial", Font.PLAIN, 4)); //TODO pull this from somewhere
    double stringHeight = g.getFontMetrics().getStringBounds("exampletext", g).getHeight();
    int xlabY = (int) Math.round(maxY);
    for(int ii = 0; ii < xBreaks.size(); ii++){
      String s = xBreaks.get(ii).dataLabel;
      double value = xBreaks.get(ii).dataBreak;
      double stringWidth = g.getFontMetrics().getStringBounds(s, g).getWidth();
      //	  	System.out.println("string width of "+s+" "+stringWidth);
      double xguideX = xDataPointToPixelLocation(value) - (int)(stringWidth/2.0);
      //	  	System.out.println("attempting to draw string "+s+" at "+xguideX+", "+xlabY+" and this string is of width "+stringWidth+" with "+s.length()+" chars");
      g.setColor(xguideColor);
      g.drawString(s, (int)Math.round(xguideX), (int)Math.round(xlabY));
      g.setColor(xTickColor);
      g.setStroke(xMajorTickStroke);
      double xtickX = xDataPointToPixelLocation(value);
      g.drawLine((int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)), (int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)+xTickLength));
      g.setColor(Colors.white); //TODO look this up somewhere
      g.drawLine((int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)), (int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(maxYDataOnPlot)));
      //	  	System.out.println("xstep "+xStep.step+" maxXDataOnPlot "+maxXDataOnPlot+" "+value+" "+(value - xStep.step/2.0 > minXDataOnPlot));
      if(value - xStep.step/2.0 > minXDataOnPlot){
        g.setStroke(xMinorTickStroke);//TODO set this from somewhere
        xtickX = xDataPointToPixelLocation(value-xStep.step/2.0);
        g.setColor(Colors.gray91); //TODO look this up somewhere
        //	  		g.drawLine(xtickX, (int)(xlabY-stringHeight-xTickLength), xtickX, minY);
        g.drawLine((int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)), (int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(maxYDataOnPlot)));
      }
      if(value+ xStep.step/2.0 < maxXDataOnPlot){
        g.setStroke(xMinorTickStroke);//TODO set this from somewhere
        xtickX = xDataPointToPixelLocation(value+xStep.step/2.0);
        g.setColor(Colors.grey91); //TODO look this up somewhere
        //	  		g.drawLine(xtickX, (int)(xlabY-stringHeight-xTickLength), xtickX, minY);
        g.drawLine((int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(minYDataOnPlot)), (int)Math.round(xtickX), (int)Math.round(yDataPointToPixelLocation(maxYDataOnPlot)));
      }
    }
  }

  private void drawYGuide(Graphics2D g) {
    Color yguideColor = Colors.grey42;// TODO pull this from somewhere
    Color yTickColor = Colors.black; //TODO pull this from somewhere
    BasicStroke yMajorTickStroke = new BasicStroke(0.4f); //TODO pull this from somewhere
    BasicStroke yMinorTickStroke = new BasicStroke(0.2f);
    int yTickLength = 2; //TODO pull this from somewhere
    g.setFont(new Font("Arial", Font.PLAIN, 4)); //TODO pull this from somewhere

    double stringHeight = g.getFontMetrics().getStringBounds("exampletext", g).getHeight();
    for(int ii = 0; ii < yBreaks.size(); ii++){


      String s = yBreaks.get(ii).dataLabel;
      double stringWidth = g.getFontMetrics().getStringBounds(s, g).getWidth();
      double value = yBreaks.get(ii).dataBreak;
      int ylabX = 0;
      double yguideY = yDataPointToPixelLocation(value) + stringHeight/2.0;
      //	  	System.out.println("attempting to draw string "+s+" at "+ylabX+", "+yguideY+" and this string is of width "+stringWidth+" with "+s.length()+" chars");
      g.setColor(yguideColor);
      //	  	AffineTransform orig = g.getTransform();
      //	  	g.rotate(-Math.PI/2.0);
      //      g.drawString(s, ylabX, yguideY);

      //	  	g.setTransform(orig);
      //	  	g.rotate(Math.PI/2.0);
      g.setColor(yTickColor);
      g.setStroke(yMajorTickStroke);
      double ytickY = yDataPointToPixelLocation(value);
      g.drawLine((int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)-1-yTickLength), (int)Math.round(ytickY), (int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)-1), (int)Math.round(ytickY));
      g.setColor(Colors.white); //TODO look this up somewhere
      g.drawLine((int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)-1), (int)Math.round(ytickY), (int)Math.round(xDataPointToPixelLocation(maxXDataOnPlot)), (int)Math.round(ytickY));
      System.out.println("xstep "+xStep.step+" maxXDataOnPlot "+maxXDataOnPlot+" "+value+" "+(value - xStep.step/2.0 > minXDataOnPlot));
      if(value - xStep.step/2.0 > minXDataOnPlot){
        g.setStroke(yMinorTickStroke);//TODO set this from somewhere
        ytickY = yDataPointToPixelLocation(value-yStep.step/2.0);
        g.setColor(Colors.gray91); //TODO look this up somewhere
        g.drawLine((int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)-1), (int)Math.round(ytickY), (int)Math.round(xDataPointToPixelLocation(maxXDataOnPlot)), (int)Math.round(ytickY));
      }
      if(value+ xStep.step/2.0 < maxXDataOnPlot){
        g.setStroke(yMinorTickStroke);//TODO set this from somewhere
        ytickY = yDataPointToPixelLocation(value+yStep.step/2.0);
        g.setColor(Colors.grey91); //TODO look this up somewhere
        g.drawLine((int)Math.round(xDataPointToPixelLocation(minXDataOnPlot)-1), (int)Math.round(ytickY), (int)Math.round(xDataPointToPixelLocation(maxXDataOnPlot)), (int)Math.round(ytickY));
      }
    }
  }

  public double xDataPointToPixelLocation(double d){
    
    System.out.println(yGuideWidth+" "+minXDataOnPlot+" "+maxXDataOnPlot+" ");
    return yGuideWidth + (minXDataOnPlot+ (d-minXDataOnPlot)/
        (maxXDataOnPlot-minXDataOnPlot)*(maxX-(yGuideWidth)));
  }

  public double yDataPointToPixelLocation(double d){ //TODO not done
    return maxY - xGuideHeight - (minYDataOnPlot+ (d-minYDataOnPlot)/
        (maxYDataOnPlot-minYDataOnPlot)*(maxY-(xGuideHeight)));
  }

  private void createPlotData(Dimension2D pixels, Dimension2D points) {

    double xmin = Double.MAX_VALUE;
    double xmax = Double.NEGATIVE_INFINITY;

    double ymin = Double.MAX_VALUE;
    double ymax = Double.NEGATIVE_INFINITY;
    Preconditions.checkState(layers.size()>0," must have at least one layer");
    for (Layer l: layers) {
      l.createPlotData(); // create layer plot data given default plot data which might be empty
      xmin = Math.min(xmin, l.minX());
      xmax = Math.max(xmax, l.maxX());

      ymin = Math.min(ymin, l.minY());
      ymax = Math.max(ymax, l.maxY());
    }

    System.out.println("minmax "+xmin+" "+xmax+" "+ymin+" "+ymax);

    xStep = new StepHolder();
    yStep = new StepHolder();
    xBreaks = calcAxisBreaksAndLimits(xmin, xmax, xStep);
    yBreaks = calcAxisBreaksAndLimits(ymin, ymax, yStep);
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


  private ArrayList<DataGuide> calcAxisBreaksAndLimits(double minval, double maxval, StepHolder stepHolder){
    double diff = maxval - minval;
    double base10 = Math.log10(diff);
    double power = Math.floor(base10);
    double base_unit = Math.pow(10, power);
    double step = base_unit / 2;
    stepHolder.step = step;
    return calcAxisBreaksAndLimits(minval,maxval, step);
  }


  private ArrayList<DataGuide> calcAxisBreaksAndLimits(double minval, double maxval, int nlabs, StepHolder stepHolder){
    double diff = maxval - minval;
    double tick_range = diff / (double)nlabs;
    // make the tick range nice looking...
    double power = Math.ceil(Math.log10(tick_range));
    double step = Math.round(tick_range / Math.pow(10,power)) * Math.pow(10, power);
    stepHolder.step = step;
    return calcAxisBreaksAndLimits(minval, maxval, step);
  }

  public class DataGuide{
    double dataBreak;
    String dataLabel;
    public DataGuide(double dataBreak, String dataLabel){
      this.dataBreak = dataBreak;
      this.dataLabel = dataLabel;
    }
  }

  private ArrayList<DataGuide> calcAxisBreaksAndLimits(double minval, double maxval, double step){
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
    ArrayList<DataGuide> toReturn = new ArrayList<DataGuide>();
    TDoubleArrayList steps = drange(minval-(step/3), maxval+(step/3), step);
    //		xRange = maxval+(step/3)-(minval-(step/3));
    System.out.println("xrange in data for plot "+xRange);
    for(int ii = 0; ii < steps.size(); ii++){
      toReturn.add(new DataGuide(steps.get(ii),NumberFormatter.format(steps.get(ii))));
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

  public void addLayer(Layer layer) {
    layers.add(layer);
  }

  public class StepHolder{
    double step;

  }



}


package org.beastmachine.ggplot.theme;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

import org.beastmachine.ggplot.facet.Facet;
import org.beastmachine.ggplot.legend.Legend;
import org.beastmachine.ggplot.visual.Paintable;
import org.beastmachine.util.Text;

import com.google.common.base.Preconditions;

public class LayoutManager implements Paintable {

  
  private Theme theme;
  private Legend legend;
  private Text ggtitle;
  private Text xlab;
  private Text ylab;
  private Facet facet;

  public LayoutManager(Theme themey, Legend legend, 
      Text ggtitle, Text xlab, Text ylab, Facet facet){
    this.theme = themey;
    this.legend = legend;
    this.ggtitle = ggtitle;
    this.xlab = xlab;
    this.ylab = ylab;
    this.facet = facet;
  }
  
  
  /**
   * Two instances of this class (one for x and one for y) will be sent 
   * to each of the top level objects in this layout for them to draw themselves
   * in the correct bounds. All they have to do is transform all of their x and y values 
   * before drawing.
   * 
   * @author wheaton
   *
   */
  public class DimensionTransformer{
    
    private double localMin;
    private double localMax;
    private double globalMin;
    private double globalMax;

    public DimensionTransformer(double localMin, double localMax, double globalMin, double globalMax){
      this.localMin = localMin;
      this.localMax = localMax;
      this.globalMin = globalMin;
      this.globalMax = globalMax;
    }
    
    public double transform(double val){
      return (val-globalMin)/globalMax * (localMax-localMin) + localMin;
    }
  }

  @Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points){
    double pixelsPerPointX = (double)pixels.getWidth()/(double)points.getWidth();
    double pixelsPerPointY = (double)pixels.getHeight()/(double)points.getHeight();
    double minPointsX = 0.0;
    double maxPointsX = points.getWidth();
    double minPointsY = 0.0;
    double maxPointsY = points.getHeight();
    
    double minPixelsX = 0.0;
    double maxPixelsX = pixels.getWidth();
    double minPixelsY = 0.0;
    double maxPixelsY = pixels.getHeight();
    
    Unit plotMargin = this.theme.get(Theme.KeyUnit.plot_margin);
    
    double[] plotMarginPoints = plotMargin.getPointsArray();
    
    double plotMarginLeft = plotMarginPoints[3];
    double plotMarginRight = plotMarginPoints[1];
    double plotMarginTop = plotMarginPoints[0];
    double plotMarginBottom = plotMarginPoints[2];
    
    minPointsX += plotMarginLeft;
    minPointsY += plotMarginTop;
    maxPointsX -= plotMarginRight;
    maxPointsY -= plotMarginBottom;
    
    minPixelsX += plotMarginLeft*pixelsPerPointX;
    maxPixelsX -= plotMarginRight*pixelsPerPointX;
    minPixelsY += plotMarginTop*pixelsPerPointY;
    maxPixelsY -= plotMarginBottom*pixelsPerPointY;
    
    assertViablePlot(minPointsX, maxPointsX, minPointsY, maxPointsY);
    
    Position legendPosition = theme.get(Theme.KeyPosition.legend_position);
    
    //print ggtitle
    g.translate(minPixelsX, minPixelsY);
    this.ggtitle.paint2D(g, new Dimension((int)(maxPixelsX-minPixelsX), (int)(maxPixelsY-minPixelsY)), new Dimension((int)(maxPointsX-minPointsX), (int)(maxPointsY-minPointsY)));
    g.translate(-minPixelsX, -minPixelsY);
    double lineHeight = this.ggtitle.getLineHeight(pixelsPerPointY);
    minPixelsY += lineHeight;
    minPointsY += lineHeight/pixelsPerPointY;
    
    //TODO I need to do the facet strip here but for the moment I'll assume no facet
    
    Dimension2D legendDim = this.legend.getRequiredPointsSize(g);
    if(legendPosition == Position.right){
      maxPointsX -= legendDim.getWidth();
      maxPixelsX -= legendDim.getWidth()*pixelsPerPointX;
      
      double legendLeft = maxPixelsX;
      double legendTop = minPixelsY;
      g.translate(legendLeft, legendTop);
      legend.paint2D(g,pixels, points);
      g.translate(-legendLeft, -legendTop);
    }
    else if(legendPosition == Position.left){
      
    }
    else if(legendPosition == Position.bottom){
      
    }
    else if(legendPosition == Position.top){
      
    }
    else{
      Preconditions.checkState(false,"legend position not supported");
    }
  }
  
  private void assertViablePlot(double minPointsX, double maxPointsX,
      double minPointsY, double maxPointsY) {
    Preconditions.checkArgument(maxPointsX > minPointsX, "Cannot create plot this size, margins, legend, labels, and tick marks force negative area plot");
    Preconditions.checkArgument(maxPointsY > minPointsY, "Cannot create plot this size, margins, legend, labels, and tick marks force negative area plot");
  }

  public static void main(String[] args){
    
    Theme theme = Theme.themeGrey();
    Unit plot_margin = theme.get(Theme.KeyUnit.plot_margin);
    double[] margins = plot_margin.getPointsArray();
    for(double d: margins){
      System.out.println(d);
    }
    Position pos = theme.get(Theme.KeyPosition.legend_position);
    System.out.println(pos == Position.right);
  }
}

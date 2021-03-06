package org.beastmachine.ggplot.geom;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.Aes;
import org.beastmachine.ggplot.GGPlot;
import org.beastmachine.ggplot.Layer;

public abstract class Geometry implements Layer.OptionSetter {
  protected Aes myMapping;
  
  public abstract void draw(DataFrame plotData, Aes myAes, GGPlot.Scalable scale, Graphics2D g);

  
	protected void setDefaults(Aes myAes, DataFrame plotData, Graphics2D g) {
	  // TODO Auto-generated method stub
  }
	
  @Override
  public void setOptions(Layer layer) {
    layer.setGeom(this);
  }

  @Override
  public void setOptions(GGPlot plot) {
    plot.setDefaultGeom(this);
  }
}

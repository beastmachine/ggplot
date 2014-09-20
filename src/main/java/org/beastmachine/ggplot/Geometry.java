package org.beastmachine.ggplot;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.beastmachine.dataframe.DataFrame;

public abstract class Geometry {
  protected Aes myMapping;
  
  public abstract void draw(DataFrame plotData, Aes myAes, Scalable scale, Graphics2D g);

  
	protected void setDefaults(Aes myAes, DataFrame plotData, Graphics2D g) {
	  // TODO Auto-generated method stub
  }
}

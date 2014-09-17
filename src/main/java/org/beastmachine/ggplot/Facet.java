package org.beastmachine.ggplot;

import org.beastmachine.ggplot.visual.Paintable;

public abstract class Facet implements Paintable{

	protected int minX;
	protected int maxX;
	protected int minY;
	protected int maxY;

	public void setArea(int minX, int maxX, int minY,
			int maxY){
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	public double getHeight(){
		return maxY - minY;
	}
	
	public double getWidth(){
		return maxX - minX;
	}

}

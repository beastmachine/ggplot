package org.beastmachine.ggplot;

import org.beastmachine.ggplot.visual.Paintable;

public abstract class Facet implements Paintable{

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

	public void setArea(int minX, int maxX, int minY,
			int maxY){
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

}

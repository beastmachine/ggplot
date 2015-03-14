package org.beastmachine.ggplot.facet;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.List;

import org.beastmachine.ggplot.Layer;
import org.beastmachine.ggplot.Scale;
import org.beastmachine.ggplot.coord.Coord;
import org.beastmachine.ggplot.theme.Theme;

public class FacetNone extends Facet {

	private Theme myDefaults;
	private Scale myScale;
	private Coord myCoord;
	private List<Layer> myLayers;

	public FacetNone(Theme myDefaults, Scale myScale, Coord myCoord, List<Layer> myLayers) {
		this.myDefaults = myDefaults;
		this.myScale = myScale;
		this.myCoord = myCoord;
		this.myLayers = myLayers;
	}

	@Override
	public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
//		myScale.paint2D(g, pixels, points);
	  myScale.drawXTicksAndText(g, pixels, points);
	  System.out.println("here");
	}

}

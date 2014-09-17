package org.beastmachine.ggplot;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.List;

public class FacetNone extends Facet {

	private Defaults myDefaults;
	private Scale myScale;
	private Coord myCoord;
	private List<Layer> myLayers;

	public FacetNone(Defaults myDefaults, Scale myScale, Coord myCoord, List<Layer> myLayers) {
		this.myDefaults = myDefaults;
		this.myScale = myScale;
		this.myCoord = myCoord;
		this.myLayers = myLayers;
	}

	@Override
	public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
		myScale.paint2D(g, pixels, points);
	}

}

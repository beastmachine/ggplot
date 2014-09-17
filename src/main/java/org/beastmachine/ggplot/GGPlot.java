package org.beastmachine.ggplot;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.ggplot.visual.Paintable;

public class Ggplot implements Paintable{

	private Scale myScale;
	private Coord myCoord;
	private Facet myFacet;
	private List<Layer> myLayers;
	private Defaults myDefaults;

	public Ggplot() {
		myDefaults = Defaults.getPrettyDefaults();
		myLayers = new ArrayList<Layer>();
		myFacet = new FacetNone(myDefaults);
	}

	public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
		double pixelsPerPointHeight = pixels.getHeight() / points.getHeight();
		double pixelsPerPointWidth = pixels.getWidth() / points.getWidth();

		int bottomMarginPixels = (int)Math.round(myDefaults.getBottumMarginPoints() * pixelsPerPointHeight);
		int topMarginPixels = (int)Math.round(myDefaults.getTopMarginPoints() * pixelsPerPointHeight);
		int leftMarginPixels = (int)Math.round(myDefaults.getLeftMarginPoints() * pixelsPerPointWidth);
		int rightMarginPixels = (int)Math.round(myDefaults.getRightMarginPoints() * pixelsPerPointWidth);

		myFacet.setArea(leftMarginPixels, (int)(pixels.getWidth() - rightMarginPixels), 
				topMarginPixels, (int)(pixels.getHeight() - bottomMarginPixels));

		myFacet.paint2D(g, pixels, points);
	}


}

package org.beastmachine.ggplot.coord;

import java.awt.geom.Point2D;

public abstract class Coord {

	public abstract Point2D coordTransform(Point2D point);
	
}

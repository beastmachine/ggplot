package org.beastmachine.ggplot.coord;

import java.awt.geom.Point2D;

public class CoordCartesian extends Coord {

	@Override
	public Point2D coordTransform(Point2D point) {
		return point;
	}

}

package org.beastmachine.ggplot;

import org.beastmachine.ggplot.visual.GeomConstants;

public class Defaults {

	private double bottomMarginInPoints;
	private double topMarginInPoints;
	private double leftMarginInPoints;
	private double rightMarginInPoints;

	public static Defaults getPrettyDefaults() {
		Defaults pretty = new Defaults();
		pretty.bottomMarginInPoints = 72.0*0.15;
		pretty.topMarginInPoints = 72.0*0.2;
		pretty.leftMarginInPoints = 72.0*0.18;
		pretty.rightMarginInPoints = 72.0*0.2;
		return pretty;
	}

	public double getBottumMarginPoints() {
		return bottomMarginInPoints;
	}

	public double getTopMarginPoints() {
		return topMarginInPoints;
	}

	public double getLeftMarginPoints() {
		return leftMarginInPoints;
	}

	public double getRightMarginPoints() {
		return rightMarginInPoints;
	}

}

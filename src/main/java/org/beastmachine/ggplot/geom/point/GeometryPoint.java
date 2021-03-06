package org.beastmachine.ggplot.geom.point;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.Aes;
import org.beastmachine.ggplot.GGPlot;
import org.beastmachine.ggplot.GlobalOptionSetter;
import org.beastmachine.ggplot.Aes.Aesthetic;
import org.beastmachine.ggplot.Layer;
import org.beastmachine.ggplot.geom.Geometry;
import org.beastmachine.ggplot.visual.Colors;

import com.google.common.base.Preconditions;

public class GeometryPoint extends Geometry {

	@Override
  public void draw(DataFrame plotData, Aes myAes, GGPlot.Scalable scale, Graphics2D g) {
		setDefaults(myAes, plotData, g);
		Preconditions.checkState(plotData.hasColumn("x"), "geom_point must have x set in aesthetic");
		Preconditions.checkState(plotData.hasColumn("y"), "geom_point must have y set in aesthetic");
		Column x = plotData.get("x");
		Column y = plotData.get("y");

		for(int ii = 0; ii < x.length(); ii++){
			
			if(myAes.getVariable(Aesthetic.shape) == null || myAes.getVariable(Aesthetic.shape).equals("circle")){
				g.setColor(Colors.black);//TODO pull this from somewhere else;
				int diameter = 3;
				
				g.fillOval((int)Math.round(scale.xDataPointToPixelLocation(x.getValue(ii))-(diameter/2)), (int)Math.round(scale.yDataPointToPixelLocation(y.getValue(ii))-(diameter/2)), diameter, diameter);//TODO grab these size values from theme
			}
			else{
				Preconditions.checkArgument(false,"only \"circle\" is supported for geom_point shape right now");
			}
		}
  }
	
	public interface OptionSetter extends GlobalOptionSetter {
	}




}

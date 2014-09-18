package org.beastmachine.ggplot;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import org.beastmachine.dataframe.Column;
import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.visual.Paintable;
import org.beastmachine.util.NumberFormatter;

import com.google.common.base.Preconditions;

import gnu.trove.list.array.TDoubleArrayList;

public class Scale implements Paintable {
	


	private Transformer xTransform;
	private Transformer yTransform;
	private Coord myCoord;
	private Aes aesthetic;
	private DataFrame data;
	private DataFrame plotData;
	private List<Layer> layers;

	public Scale(DataFrame data, Aes aesthetic, Coord myCoord, List<Layer> layers) {
	 this.myCoord = myCoord;
	 this.aesthetic = aesthetic;
	 this.data = data;
	 this.layers = layers;
	 xTransform = new IdentityTransform();
	 yTransform = new IdentityTransform();
	 createPlotData();
  }

	private void createPlotData() {
	  plotData = new DataFrame();
	  for(Aes.Aesthetic aes: aesthetic.getSetAesthetics()){
	  	String val = aesthetic.getVariable(aes);
	  	if(data.hasColumn(val)){
	  		if(aes == Aes.Aesthetic.x){
	  			plotData.c(aes.toString(), xTransform.transform(data.get(val)));
	  		}
	  		else if(aes == Aes.Aesthetic.y){
	  			plotData.c(aes.toString(), yTransform.transform(data.get(val)));
	  		}
	  		else{
	  			Preconditions.checkState(false, "Aesthetic ",aes," not currently supported. TODO"); //TODO
	  			plotData.c(aes.toString(), data.get(val));
	  		}
	  	}
	  }
	  for(Layer l: layers){
	  	
	  }
  }

	public void setXTransform(Transformer trans){
		xTransform = trans;
		createPlotData();
	}
	
	public void setYTransform(Transformer trans){
		yTransform = trans;
		createPlotData();
	}


	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval){
		double diff = maxval - minval;
		double base10 = Math.log10(diff);
		double power = Math.floor(base10);
		double base_unit = Math.pow(10, power);
		double step = base_unit / 2;
		return calcAxisBreaksAndLimits(minval,maxval, step);
	}

	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, int nlabs){
		double diff = maxval - minval;
		double tick_range = diff / (double)nlabs;
		// make the tick range nice looking...
		double power = Math.ceil(Math.log10(tick_range));
		double step = Math.round(tick_range / Math.pow(10,power)) * Math.pow(10, power);
		return calcAxisBreaksAndLimits(minval, maxval, step);
	}

	private ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, double step){
		/**
		 * 
		 * Calculates axis breaks and suggested limits.
		 * The limits are computed as minval/maxval -/+ 1/3 step of ticks.
		 * Parameters
		 * ----------
		 * minval : number
		 *   lowest value on this axis
		 * maxval : number
		 *   higest number on this axis
		 * nlabs : int
		 *   number of labels which should be displayed on the axis
		 * Default: None
		 **/
		ArrayList<String> toReturn = new ArrayList<String>();
		TDoubleArrayList steps = drange(minval-(step/3), maxval+(step/3), step);
		for(int ii = 0; ii < steps.size(); ii++){
			toReturn.add(NumberFormatter.format(steps.get(ii)));
		}
		return toReturn;
	}

	private TDoubleArrayList drange(double start, double stop, double step){
		/**
		 * Compute the steps in between start and stop
		 * Only steps which are a multiple of `step` are used.
		 */
		TDoubleArrayList toReturn = new TDoubleArrayList();
		double  r = (Math.floor(start / step) * step) + step; // # the first step higher than start
		//# all subsequent steps are multiple of "step"!
		while(r < stop){
			toReturn.add(r);
			r += step;
		}
		return toReturn;
	}
	
	public interface Transformer{
		public Column transform(Column c);
	}
	
	public class IdentityTransform implements Transformer {
	  @Override
	  public Column transform(Column c) {
		  return c; // no deep copy for memory considerations, but could be dangerous
	  }
  }

	@Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
	  
	  
  }


}


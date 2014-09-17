package org.beastmachine.ggplot;

import java.util.ArrayList;

import org.beastmachine.util.NumberFormatter;

import gnu.trove.list.array.TDoubleArrayList;

public abstract class Scale {


	public ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval){
		double diff = maxval - minval;
		double base10 = Math.log10(diff);
		double power = Math.floor(base10);
		double base_unit = Math.pow(10, power);
		double step = base_unit / 2;
		return calcAxisBreaksAndLimits(minval,maxval, step);
	}

	public ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, int nlabs){
		double diff = maxval - minval;
		double tick_range = diff / (double)nlabs;
		// make the tick range nice looking...
		double power = Math.ceil(Math.log10(tick_range));
		double step = Math.round(tick_range / Math.pow(10,power)) * Math.pow(10, power);
		return calcAxisBreaksAndLimits(minval, maxval, step);
	}

	public ArrayList<String> calcAxisBreaksAndLimits(double minval, double maxval, double step){
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

	public TDoubleArrayList drange(double start, double stop, double step){
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


}


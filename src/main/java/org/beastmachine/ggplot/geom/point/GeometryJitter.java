package org.beastmachine.ggplot.geom.point;

import java.awt.Graphics2D;

import org.beastmachine.dataframe.DataFrame;
import org.beastmachine.ggplot.Aes;
import org.beastmachine.ggplot.GGPlot.Scalable;
import org.beastmachine.ggplot.GlobalOptionSetter;
import org.beastmachine.ggplot.geom.Geometry;

import com.google.common.base.Preconditions;

public class GeometryJitter extends Geometry {

  public interface OptionSetter extends GlobalOptionSetter {

  }

  @Override
  public void draw(DataFrame plotData, Aes myAes, Scalable scale, Graphics2D g) {
    Preconditions.checkState(false, "geom_jitter not yet supported");
  }

}

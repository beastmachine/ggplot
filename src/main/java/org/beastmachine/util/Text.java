package org.beastmachine.util;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

import org.beastmachine.ggplot.theme.TextFormat;
import org.beastmachine.ggplot.visual.Paintable;

import com.google.common.base.Preconditions;

public class Text implements Paintable {

	private String content;
	private TextFormat format;

	public Text(String content, TextFormat format){
		this.content = content;
		this.format = format;
	}

  @Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
    double pixelsPerPointY = (double)pixels.getHeight()/(double)points.getHeight();
    double pixelsPerPointX = (double)pixels.getWidth()/(double)points.getWidth();
    double textWidth = format.getLineWidth(g, this.content, pixelsPerPointX);
    double hJust = format.getHorizontalJustification();
    if(hJust == 0.5){
      g.drawString(this.content, (float)(hJust*pixels.getWidth()-textWidth/2.0), (float)format.getLineHeight(pixelsPerPointY));
    }
    else{
      Preconditions.checkArgument(false, "hJust not supported except centered for now.... lalala I cant hear you");
    }
    
  }

  public double getLineHeight(double pixelsPerPoint) {
    return format.getLineHeight(pixelsPerPoint);
  }

  public Dimension2D getRequiredPointsSize(Graphics2D g) {
    return new GDimension2D(format.getLineWidth(g, this.content, 1),format.getLineHeight(1));
  }

 
}

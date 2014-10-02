package org.beastmachine.ggplot.geom.point;
import static org.beastmachine.ggplot.visual.Colors.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.IOException;

import javax.swing.JFrame;

import org.beastmachine.gfx.Paintable;
import org.beastmachine.gfx.RealPanel;
import org.beastmachine.gfx.Renderable;
import org.beastmachine.ggplot.pdf.PaintPDF;
import org.beastmachine.ggplot.theme.Unit;
import org.beastmachine.ggplot.theme.Unit.UnitType;

public class AllPointsTest {
  public static void main(String[] args) throws IOException {
    RealPanel rp = new RealPanel();
    rp.setPreferredSize(new Dimension(400,400));
    final Color fillColor = red;
    final Color drawColor = black;
    final Unit margin = new Unit(UnitType.lines, 1,1,1,1);
    
    for (int ii = 0; ii <= 25; ii++) {
      final int shape = ii;
      rp.addRenderable(new Renderable() {

        @Override
        public Paintable getPaintable(Dimension2D pixelSize,
            Dimension2D pointSize) {
          int row = shape/5;
          int col = shape%5;
          double pixelsPerPoint = pixelSize.getWidth()/pointSize.getWidth();
          double[] margins = margin.getPixelsArray(pixelsPerPoint);
          double xPix = 
              margins[3] + 
              (pixelSize.getWidth() - margins[3] - margins[1]) *
              col / 4.0;
          double yPix =
              margins[0] +
              (pixelSize.getHeight() - margins[0] - margins[2]) *
              row / 5.0;
          return new DrawPoint(shape, 2, xPix, yPix, pixelsPerPoint, fillColor, drawColor);
        }
      });
    }
    
    JFrame frame = new JFrame();
    frame.add(rp);
    frame.pack();
    frame.setVisible(true);
    
    PaintPDF.paintToPDF(rp, new Dimension(800, 800),
        new Dimension(216,216), "/Users/Peter/Documents/doubleSize.pdf");
  }
}

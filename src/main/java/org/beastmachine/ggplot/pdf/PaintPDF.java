package org.beastmachine.ggplot.pdf;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.beastmachine.ggplot.visual.Paintable;

public class PaintPDF {

  private static Properties pageSize(Dimension pointSize) {
    Properties p = new Properties();

    p.setProperty(PDF2D.PAGE_SIZE,
        PDF2D.CUSTOM_PAGE_SIZE);
    p.setProperty(PDF2D.CUSTOM_PAGE_SIZE,
        pointSize.width + ", " + pointSize.height);
    return p;
  }

  private static Properties pageMargins(Insets pointInsets) {
    Properties p = new Properties();

    p.setProperty(PDF2D.PAGE_MARGINS,
        PDF2D.CUSTOM_PAGE_MARGINS);
    p.setProperty(PDF2D.CUSTOM_PAGE_MARGINS,
        pointInsets.top + ", " + pointInsets.left + ", " +
            pointInsets.bottom + ", " +pointInsets.right);
    return p;
  }

  public static void paintToPDF(Paintable paint, Dimension pixelSize,
      Dimension pointSize, String pdfFileName) throws IOException {
    PDF2D g = new PDF2D(new File(pdfFileName), pixelSize);
    g.setProperties(pageSize(pointSize));
    g.setProperties(pageMargins(new Insets(0, 0, 0, 0)));
    g.startExport();
    paint.paint2D(g);
    g.endExport();
  }
}

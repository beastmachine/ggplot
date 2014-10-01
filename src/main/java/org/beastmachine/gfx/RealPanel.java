package org.beastmachine.gfx;

import static org.beastmachine.ggplot.visual.Colors.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.beastmachine.ggplot.theme.Rect;
import org.beastmachine.ggplot.theme.Line.LineType;
import org.beastmachine.util.GDimension2D;

public class RealPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private double realScreenWidthInches;
  private double realScreenHeightInches;
  private double dpi;
  private List<Renderable> myPreObjects;
  private List<Paintable> myObjects;

  public RealPanel() {
    super();
    this.setBackground(white);
    /*
     * Based on my screen. There is no way to get real screen dimensions
     *  other than asking the user.
     */
    realScreenWidthInches = 11.28;
    realScreenHeightInches = 7.05;
    this.dpi = calculateDpi();
    this.myPreObjects = new ArrayList<Renderable>();
    this.myObjects = new ArrayList<Paintable>();
    this.addComponentListener(new PaintableMaker());
  }

  private double calculateDpi() {
    GraphicsDevice gd = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    double wDpi = width/realScreenWidthInches;
    double hDpi = height/realScreenHeightInches;

    return (wDpi+hDpi)/2.0;
  }

  private class PaintableMaker extends ComponentAdapter {
    @Override
    public void componentResized(ComponentEvent e) {
      synchPaintables();
    }
  }

  private void synchPaintables() {
    myObjects.clear();
    Dimension pixelSize =
        new Dimension(RealPanel.this.getWidth(),
            RealPanel.this.getHeight());
    Dimension2D pointSize =
        new GDimension2D(pixelSize.width/dpi*72, pixelSize.height/dpi*72);
    for (Renderable r : myPreObjects) {
      myObjects.add(r.getPaintable(pixelSize,pointSize));
    }
  }

  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    if (myObjects.size() != myPreObjects.size()) {
      synchPaintables();
    }
    for (Paintable p : myObjects) {
      p.paint(g2d);
    }
  }

  public void addRenderable(Renderable r) {
    myPreObjects.add(r);
  }

  public void setScreenDimension(Dimension2D inches) {
    this.realScreenWidthInches = inches.getWidth();
    this.realScreenHeightInches = inches.getHeight();
  }
}

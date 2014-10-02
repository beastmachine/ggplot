package org.beastmachine.gfx;

import static org.beastmachine.ggplot.visual.Colors.white;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.beastmachine.ggplot.visual.Graphics2DState;
import org.beastmachine.util.GDimension2D;

public class RealPanel extends JPanel implements org.beastmachine.ggplot.visual.Paintable {
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
    this.realScreenWidthInches = 11.28;
    this.realScreenHeightInches = 7.05;
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
    repaint();
  }

  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    Graphics2DState state = new Graphics2DState(g2d);

    setAntiAlias(g2d);
    if (myObjects.size() != myPreObjects.size()) {
      synchPaintables();
    }
    for (Paintable p : myObjects) {
      p.paint(g2d);
    }

    state.restore(g2d);
  }

  private void setAntiAlias(Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public void setDpi(double dpi) {
    this.dpi = dpi;
    synchPaintables();
  }

  public void addRenderable(Renderable r) {
    myPreObjects.add(r);
  }

  public void setScreenDimension(Dimension2D inches) {
    this.realScreenWidthInches = inches.getWidth();
    this.realScreenHeightInches = inches.getHeight();
  }

  @Override
  public void paint2D(Graphics2D g, Dimension2D pixels, Dimension2D points) {
    Graphics2DState state = new Graphics2DState(g);

    g.setColor(white);
    g.drawRect(0, 0, (int)pixels.getWidth(), (int)pixels.getHeight());
    setAntiAlias(g);

    List<Paintable> toPaint = new ArrayList<Paintable>();
    for (Renderable r : myPreObjects) {
      toPaint.add(r.getPaintable(pixels, points));
    }
    for (Paintable p : toPaint) {
      p.paint(g);
    }
    state.restore(g);
  }
}

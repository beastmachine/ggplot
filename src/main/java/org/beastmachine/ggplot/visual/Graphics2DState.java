package org.beastmachine.ggplot.visual;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;

public class Graphics2DState {

  private final Color bg;
  private final Shape clip;
  private final Color color;
  private final Composite composite;
  private final Font font;
  private final Paint paint;
  private final RenderingHints renderingHints;
  private final Stroke stroke;
  private final AffineTransform transform;

  public Graphics2DState(Graphics2D g) {
    bg = g.getBackground();
    clip = g.getClip();
    color = g.getColor();
    composite = g.getComposite();
    font = g.getFont();
    paint = g.getPaint();
    renderingHints = g.getRenderingHints();
    stroke = g.getStroke();
    transform = g.getTransform();
  }

  public void restore(Graphics2D g) {
    g.setBackground(bg);
    g.setClip(clip);
    g.setColor(color);
    g.setComposite(composite);
    g.setFont(font);
    g.setPaint(paint);
    for (Object keyO : renderingHints.keySet()) {
      Key key = (Key)keyO;
      g.setRenderingHint(key, renderingHints.get(keyO));
    }
    g.setStroke(stroke);
    g.setTransform(transform);
  }
}
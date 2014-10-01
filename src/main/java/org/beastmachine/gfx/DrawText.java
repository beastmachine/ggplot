package org.beastmachine.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DrawText implements Paintable {

  private Color textColor;
  private Font textFont;
  private String textString;
  private float textX;
  private float textY;

  @Override
  public void paint(Graphics2D g) {
    g.setColor(textColor);
    g.setFont(textFont);
    g.drawString(textString, textX, textY);
  }
}

package org.beastmachine.gfx;

import java.awt.geom.Dimension2D;

public interface Renderable {
  public Paintable getPaintable(Dimension2D pixelSize, Dimension2D pointSize);
}

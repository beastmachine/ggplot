package org.beastmachine.ggplot.geom.point;

import static org.beastmachine.ggplot.visual.Colors.invisible;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import org.beastmachine.gfx.DrawShape;

public class DrawPoint extends DrawShape {

  public DrawPoint(int shape, double sizeIn075mm,
      double xPix, double yPix,
      double pixelsPerPoint,
      Color fillColor, Color drawColor) {
    super(shapeForPoint(shape,xPix,yPix,sizeIn075mm, pixelsPerPoint),
        fillForPoint(shape,fillColor,drawColor),
        drawForPoint(shape,drawColor), initStroke(pixelsPerPoint));
  }

  private static Color drawForPoint(int shape, Color drawColor) {
    if (shape >= 15 && shape <= 18) {
      return invisible;
    } else {
      return drawColor;
    }
  }

  private static Color fillForPoint(int shape, Color fillColor, Color drawColor) {
    if (shape >= 0 && shape <= 14) {
      return invisible;
    } else if (shape >= 15 && shape <= 20) {
      return drawColor;
    } else if (shape >= 21 && shape <= 25) {
      return fillColor;
    } else {
      throw new IllegalArgumentException(shape+" not a recognized shape");
    }
  }

  private static Shape shapeForPoint(int shape,
      double x,
      double y,
      double sizeIn075mm,
      double pixelsPerPoint) {
    Shape toReturn = null;
    switch(shape) {
    case 0:
      toReturn = new Square(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 1:
      toReturn = new Circle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 2:
      toReturn = new Triangle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 3:
      toReturn = new Cross(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 4:
      toReturn = new Ex(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 5:
      toReturn = new Diamond(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 6:
      toReturn = new DownTriangle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 7:
      toReturn = new ExBox(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 8:
      toReturn = new Asterisk(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 9:
      toReturn = new CrossDiamond(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 10:
      toReturn = new CrossCircle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 11:
      toReturn = new Hexagram(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 12:
      toReturn = new CrossBox(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 13:
      toReturn = new ExCircle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 14:
      toReturn = new TriangleSquare(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 15:
      toReturn = new Square(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 16:
      toReturn = new Circle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 17:
      toReturn = new Triangle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 18:
      toReturn = new Diamond(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 19:
      toReturn = new Circle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 20:
      toReturn = new Circle(x, y, sizeIn075mm*2.0/3.0, pixelsPerPoint);
      break;
      
    case 21:
      toReturn = new Circle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 22:
      toReturn = new Square(x, y, sizeIn075mm*0.886, pixelsPerPoint);
      break;
      
    case 23:
      toReturn = new Diamond(x, y, sizeIn075mm*0.886, pixelsPerPoint);
      break;
      
    case 24:
      toReturn = new Triangle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    case 25:
      toReturn = new DownTriangle(x, y, sizeIn075mm, pixelsPerPoint);
      break;
      
    default:
      throw new IllegalArgumentException(shape+" not an acceptable shape value");
    }
    return toReturn;
  }

  private static BasicStroke initStroke(double pixelsPerPoint) {
    return new BasicStroke(
        (float)(pixelsPerPoint*0.75),
        BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND);
  }

}

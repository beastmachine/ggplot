package org.beastmachine.ggplot.png;

import static java.lang.Math.round;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import org.beastmachine.ggplot.visual.Paintable;

public class PaintPng {
  public static void paintToPng(Paintable paint, Dimension pixelSize, double dpi, String outFilePath) throws IOException {
    BufferedImage img =
        new BufferedImage(pixelSize.width, pixelSize.height,
            BufferedImage.TYPE_INT_RGB);
    Dimension pointsSize = new Dimension((int)round(72*pixelSize.width/dpi), (int)round(72*pixelSize.height/dpi));
    paint.paint2D((Graphics2D)img.getGraphics(), pixelSize, pointsSize);
    savePng(img, dpi, new File(outFilePath));
  }

  public static void savePng(BufferedImage img, double dpi, File output) throws IOException {
    output.delete();

    final String formatName = "png";

    for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
      ImageWriter writer = iw.next();
      ImageWriteParam writeParam = writer.getDefaultWriteParam();
      ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
      IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
      if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
        continue;
      }

      setDPI(metadata, dpi);

      final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
      try {
        writer.setOutput(stream);
        writer.write(metadata, new IIOImage(img, null, metadata), writeParam);
      } finally {
        stream.close();
      }
      break;
    }

  }

  private static void setDPI(IIOMetadata metadata, double dpi) throws IIOInvalidTreeException {
    // for PNG, it's dots per millimeter
    double dotsPerMilli = dpi / 25.4;

    IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
    horiz.setAttribute("value", Double.toString(dotsPerMilli));

    IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
    vert.setAttribute("value", Double.toString(dotsPerMilli));

    IIOMetadataNode dim = new IIOMetadataNode("Dimension");
    dim.appendChild(horiz);
    dim.appendChild(vert);

    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
    root.appendChild(dim);

    metadata.mergeTree("javax_imageio_1.0", root);
  }
}

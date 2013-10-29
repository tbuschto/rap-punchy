package com.eclipsesource.rap.punchy.ece13;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public class ImageUtil {

  public static Image resizeImageToFill( Image image, int targetWidth, int targetHeight ) {
    int imageWidth = image.getBounds().width;
    int imageHeight = image.getBounds().height;
    Rectangle result = calculateToFill( imageWidth, imageHeight, targetWidth, targetHeight );
    return resizeImage( image, result.width, result.height );
  }

  static Rectangle calculateToFill( int srcWidth, int srcHeight, int targetWidth, int targetHeight )
  {
    Rectangle result = new Rectangle( 0, 0, srcWidth, srcHeight );
    float widthScale = targetWidth / ( float )srcWidth;
    float heightScale = targetHeight / ( float )srcHeight;
    if( heightScale < widthScale && heightScale != 1 ) {
      result.height = Math.round( srcHeight * widthScale );
      result.width = Math.round( srcWidth * widthScale );
    } else if( widthScale < heightScale && widthScale != 1 ) {
      result.height = Math.round( srcHeight * heightScale );
      result.width = Math.round( srcWidth * heightScale );
    }
    return result;
  }

  public static Image resizeImageToFit( Image image, int targetWidth, int targetHeight ) {
    int imageWidth = image.getBounds().width;
    int imageHeight = image.getBounds().height;
    float scaleWidth = imageWidth / ( float )targetWidth;
    float scaleHeight = imageHeight / ( float )targetHeight;
    int resultWidth = 0;
    int resultHeight = 0;
    if( scaleHeight > scaleWidth ) {
      resultHeight = targetHeight;
      resultWidth = Math.round( imageWidth / scaleHeight );
    } else {
      resultHeight = Math.round( imageHeight / scaleWidth );
      resultWidth = targetWidth;
    }
    return resizeImage( image, resultWidth, resultHeight );
  }

  public static Image resizeImage( Image image, int width, int height ) {
    return new Image( image.getDevice(), image.getImageData().scaledTo( width, height ) );
  }

  private ImageUtil() {
    // prevent instantiation
  }
}

package model.converter;

import model.image.Image;
import model.elementColorsLoader.IMosaicElementColorLoader;
import model.color.Color;

public class ImagePixelColorsToElementColors implements IImagePixelColorsToElementColors {
    IPixelColorToElementColor pixelColorToElementColor;
    IMosaicElementColorLoader mosaicElementColorLoader;

    public ImagePixelColorsToElementColors(IPixelColorToElementColor pixelColorToElementColor, IMosaicElementColorLoader mosaicElementColorLoader) {
        this.pixelColorToElementColor = pixelColorToElementColor;
        this.mosaicElementColorLoader = mosaicElementColorLoader;
    }


    @Override
    public void convertPixelColorsToElementColors(Image image) {

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color convertedColor = pixelColorToElementColor.getClosestElementColor(image.getDotColor(i, j), mosaicElementColorLoader);
                image.setDotColor(i, j, convertedColor);
            }
        }
    }
}

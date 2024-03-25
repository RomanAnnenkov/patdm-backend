package model.converter;

import model.image.Image;
import model.elementColorsLoader.IMosaicElementColorLoader;
import model.color.Color;

import java.util.HashMap;
import java.util.Map;

public class ImagePixelColorsToElementColors implements IImagePixelColorsToElementColors {
    IPixelColorToElementColor pixelColorToElementColor;
    IMosaicElementColorLoader mosaicElementColorLoader;

    public ImagePixelColorsToElementColors(IPixelColorToElementColor pixelColorToElementColor, IMosaicElementColorLoader mosaicElementColorLoader) {
        this.pixelColorToElementColor = pixelColorToElementColor;
        this.mosaicElementColorLoader = mosaicElementColorLoader;
    }


    @Override
    public void convertPixelColorsToElementColors(Image image) {

        Map<Color, Color> foundColorRelations = new HashMap<>();

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color currentColor = image.getDotColor(i, j);
                if (foundColorRelations.containsKey(currentColor)) {
                    image.setDotColor(i, j, foundColorRelations.get(currentColor));
                } else {
                    Color convertedColor = pixelColorToElementColor.getClosestElementColor(currentColor, mosaicElementColorLoader);
                    foundColorRelations.put(currentColor, convertedColor);
                    image.setDotColor(i, j, convertedColor);
                }
            }
        }
    }
}

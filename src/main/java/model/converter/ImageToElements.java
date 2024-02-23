package model.converter;

import model.color.Color;
import model.image.Image;

public class ImageToElements implements IElementToPixel {
    @Override
    public Image decrease(Image image) {
        int elementSide = getElementSide(image);
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        int resizedHeight = imageHeight / elementSide;
        int resizedWidth = imageWidth / elementSide;
        Image result = new Image(resizedHeight, resizedWidth);

        int imageHeightIndex = elementSide / 2;
        int imageWidthIndex = elementSide / 2;
        for (int i = 0; i < resizedHeight; i++) {
            for (int j = 0; j < resizedWidth; j++) {
                result.setDotColor(i, j, image.getDotColor(imageHeightIndex, imageWidthIndex));
                imageWidthIndex += elementSide;
            }
            imageHeightIndex += elementSide;
            imageWidthIndex = elementSide / 2;
        }
        return result;
    }

    private int getElementSide(Image image) {
        int side = image.getWidth();
        int count = 0;
        int centerWidth = image.getWidth() / 2;
        Color previousColor = image.getDotColor(0, centerWidth);
        for (int i = 0; i < image.getHeight(); i++) {
            Color currentColor = image.getDotColor(i, centerWidth);
            if (currentColor.equals(previousColor)) {
                count++;
            } else {
                if (side > count) {
                    side = count;
                }
                previousColor = currentColor;
                count = 1;
            }
        }
        return side;
    }
}

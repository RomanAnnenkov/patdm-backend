package model.converter;

import model.color.Color;
import model.elementColorsLoader.IMosaicElementColorLoader;

import java.util.Map;

public class PixelColorToElementColor implements IPixelColorToElementColor {

    @Override
    public Color getClosestElementColor(Color pixelColor, IMosaicElementColorLoader elementColors) {
        Color closestColor = null;
        int colorDifference = Integer.MAX_VALUE;
        Map<Color, String> elements = elementColors.getColors();
        for (Color elementColor : elements.keySet()) {
            int redDifference = elementColor.getRed() - pixelColor.getRed();
            if (redDifference < 0) {
                redDifference *= -1;
            }
            int greenDifference = elementColor.getGreen() - pixelColor.getGreen();
            if (greenDifference < 0) {
                greenDifference *= -1;
            }
            int blueDifference = elementColor.getBlue() - pixelColor.getBlue();
            if (blueDifference < 0) {
                blueDifference *= -1;
            }
            int currentColorDifference = redDifference + greenDifference + blueDifference;

            if (currentColorDifference < colorDifference) {
                colorDifference = currentColorDifference;
                closestColor = elementColor;
            }
        }
        return closestColor;
    }

}

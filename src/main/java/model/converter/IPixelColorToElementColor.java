package model.converter;

import model.color.Color;
import model.elementColorsLoader.IMosaicElementColorLoader;

public interface IPixelColorToElementColor {
    Color getClosestElementColor(Color pixelColor, IMosaicElementColorLoader elementColors);

}

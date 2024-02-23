package model.elementColorsLoader;

import model.color.Color;

import java.util.Map;

public interface IMosaicElementColorLoader {
    void load();

    Map<Color, String> getColors();
}

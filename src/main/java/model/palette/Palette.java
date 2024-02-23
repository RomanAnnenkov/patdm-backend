package model.palette;

import model.color.Color;
import model.image.Image;
import model.tableMap.IUniqueSymbols;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Palette {
    private final IUniqueSymbols iUniqueSymbols;
    private final Color[] uniqueColors;
    private final Map<Color, String> colorSymbolMap;
    private final Image resizedImage;
    private Map<Color, Integer> colorCountMap;


    public Palette(IUniqueSymbols iUniqueSymbols, Image resizedImage) {
        this.iUniqueSymbols = iUniqueSymbols;
        this.resizedImage = resizedImage;
        this.uniqueColors = getUniqueColors(resizedImage);
        this.colorSymbolMap = getSymbolicColorMap();
    }

    public Map<Color, String> getColorSymbolMap() {
        return colorSymbolMap;
    }

    private Color[] getUniqueColors(Image image) {
        Set<Color> result = new HashSet<>();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                result.add(image.getDotColor(i, j));
            }
        }
        return result.toArray(Color[]::new);
    }

    private Map<Color, String> getSymbolicColorMap() {
        Map<Color, String> symbolicColorMap = new HashMap<>();
        int symbolsCount = uniqueColors.length;
        String[] uniqueSymbols = iUniqueSymbols.getUniqueSymbols(symbolsCount);
        for (int i = 0; i < uniqueColors.length; i++) {
            symbolicColorMap.put(uniqueColors[i], uniqueSymbols[i]);
        }
        return symbolicColorMap;
    }

    public Map<Color, Integer> getColorCountMap() {
        if (colorCountMap != null) {
            return colorCountMap;
        } else {
            Map<Color, Integer> newColorCountMap = new HashMap<>();
            for (int i = 0; i < resizedImage.getHeight(); i++) {
                for (int j = 0; j < resizedImage.getWidth(); j++) {
                    Color currentColor = resizedImage.getDotColor(i, j);
                    if (!newColorCountMap.containsKey(currentColor)) {
                        newColorCountMap.put(currentColor, 1);
                    } else {
                        newColorCountMap.put(currentColor, newColorCountMap.get(currentColor) + 1);
                    }
                }
            }
            colorCountMap = newColorCountMap;
            return colorCountMap;
        }
    }

    public Image generatePreview() {
        return resizedImage;
    }
}

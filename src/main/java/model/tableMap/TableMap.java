package model.tableMap;

import model.color.Color;
import model.image.Image;
import model.palette.Palette;

import java.util.*;

public class TableMap implements ITableMap {

    private final Palette palette;

    public TableMap(Palette palette) {
        this.palette = palette;
    }

    @Override
    public String[][] getTableMap() {
        Image image = palette.generatePreview();
        Map<Color, String> colorSymbolMap = palette.getColorSymbolMap();
        String[][] tableMap = new String[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                tableMap[i][j] = colorSymbolMap.get(image.getDotColor(i, j));
            }
        }
        return tableMap;
    }


}

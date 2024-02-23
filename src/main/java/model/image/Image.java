package model.image;

import model.color.Color;

public class Image {
    private final Color[][] matrix;

    public Image(int height, int width) {
        matrix = new Color[height][width];
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int getHeight() {
        return matrix.length;
    }

    public void setDotColor(int height, int width, Color color) {
        matrix[height][width] = color;
    }

    public Color getDotColor(int height , int width) {
        return matrix[height][width];
    }
}

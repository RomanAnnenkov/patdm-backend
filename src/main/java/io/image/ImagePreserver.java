package io.image;

import model.color.Color;
import model.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePreserver implements IOutputImage {
    @Override
    public void saveImage(Image image, String filePath) throws IOException {
        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage imageForSave = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = image.getDotColor(i, j);
                int pixel = (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
                imageForSave.setRGB(j, i, pixel);
            }
        }

        ImageIO.write(imageForSave, "png", new File(filePath));

    }
}

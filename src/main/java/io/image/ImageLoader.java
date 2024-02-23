package io.image;

import exceptions.ImageIsNotPNGExceptions;
import ij.IJ;
import ij.ImagePlus;
import model.color.Color;
import model.image.Image;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageLoader implements IInputImage {
    @Override
    public Image loadImage(String filepath) throws FileNotFoundException, ImageIsNotPNGExceptions {
        File file = new File(filepath);
        if (!file.exists() || !file.canRead()) {
            throw new FileNotFoundException(file + " not found");
        }
        if (!filepath.matches(".+\\.png")) {
            throw new ImageIsNotPNGExceptions("program support only png files");
        }

        ImagePlus inputImage = IJ.openImage(filepath);
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        Image resultImage = new Image(height, width);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] pixelElements = inputImage.getPixel(j, i);
                resultImage.setDotColor(i, j, new Color(pixelElements[0], pixelElements[1], pixelElements[2]));
            }
        }

        return resultImage;
    }
}

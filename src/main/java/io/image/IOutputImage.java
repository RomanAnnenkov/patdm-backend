package io.image;

import model.image.Image;

import java.io.IOException;

    public interface IOutputImage {
    void saveImage(Image image, String filePath) throws IOException;
}

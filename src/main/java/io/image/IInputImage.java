package io.image;

import exceptions.ImageIsNotPNGExceptions;
import model.image.Image;

import java.io.FileNotFoundException;

public interface IInputImage {
    Image loadImage(String filepath) throws FileNotFoundException, ImageIsNotPNGExceptions;
}

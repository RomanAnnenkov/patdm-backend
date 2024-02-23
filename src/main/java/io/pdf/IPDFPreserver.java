package io.pdf;

import com.itextpdf.text.DocumentException;
import exceptions.MosaicIsTooBigException;

import java.io.FileNotFoundException;

public interface IPDFPreserver {
    void savePDF(String filepath) throws FileNotFoundException, DocumentException, MosaicIsTooBigException;
}

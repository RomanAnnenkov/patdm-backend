import com.itextpdf.text.DocumentException;
import exceptions.ImageIsNotPNGExceptions;
import exceptions.MosaicIsTooBigException;
import io.PathGenerator;
import io.image.ImageLoader;
import io.image.ImagePreserver;
import io.pdf.PDFMapPreserver;
import io.pdf.PDFPalettePreserver;
import model.converter.*;
import model.elementColorsLoader.IMosaicElementColorLoader;
import model.image.Image;
import model.elementColorsLoader.DMCElementColors;
import model.palette.Palette;
import model.tableMap.AlphabeticSymbols;
import model.tableMap.ITableMap;
import model.tableMap.TableMap;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ImageIsNotPNGExceptions, DocumentException, MosaicIsTooBigException {

        String filePath = "storage/client_1/sen.png";
        ImageLoader loader = new ImageLoader();
        Image image = loader.loadImage(filePath);

        IElementToPixel converterSize = new ImageToElements();
        Image resizedImage = converterSize.decrease(image);
        ImagePreserver imagePreserver = new ImagePreserver();
        PathGenerator pathGenerator = new PathGenerator(filePath);
        imagePreserver.saveImage(resizedImage, pathGenerator.getPath("resized", "png"));
        IMosaicElementColorLoader mosaicElementColorLoader = DMCElementColors.getInstance();
        IImagePixelColorsToElementColors colorConverter = new ImagePixelColorsToElementColors(new PixelColorToElementColor(), mosaicElementColorLoader);
        colorConverter.convertPixelColorsToElementColors(resizedImage);

        Palette palette = new Palette(new AlphabeticSymbols(), resizedImage);

        imagePreserver.saveImage(palette.generatePreview(), pathGenerator.getPath("preview", "png"));

        PDFPalettePreserver pdfPalettePreserver = new PDFPalettePreserver(palette);
        pdfPalettePreserver.savePDF(pathGenerator.getPath("palette", "pdf"));

        ITableMap tableMap = new TableMap(palette);
        String[][] forPrint = tableMap.getTableMap();

        PDFMapPreserver pdfMapPreserver = new PDFMapPreserver(forPrint);
        pdfMapPreserver.savePDF(pathGenerator.getPath("map", "pdf"));



    }
}

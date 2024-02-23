package model.server;

import com.itextpdf.text.DocumentException;
import exceptions.ImageIsNotPNGExceptions;
import exceptions.MosaicIsTooBigException;
import io.PathGenerator;
import io.image.ImageLoader;
import io.image.ImagePreserver;
import io.pdf.PDFMapPreserver;
import io.pdf.PDFPalettePreserver;
import model.converter.*;
import model.elementColorsLoader.DMCElementColors;
import model.elementColorsLoader.IMosaicElementColorLoader;
import model.image.Image;
import model.palette.Palette;
import model.tableMap.AlphabeticSymbols;
import model.tableMap.ITableMap;
import model.tableMap.TableMap;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class RestServer {

    @GetMapping("/health-check")
    public boolean healthCheck() {
        return true;
    }

    @GetMapping("/process-file")
    public List<String> processPng(@RequestParam(value = "filePath", defaultValue = "storage/client_1/sen.png") String filePath) throws ImageIsNotPNGExceptions, IOException, DocumentException, MosaicIsTooBigException {
        List<String> generatedFiles = new ArrayList<>();

        ImageLoader loader = new ImageLoader();
        Image image = loader.loadImage(filePath);

        IElementToPixel converterSize = new ImageToElements();
        Image resizedImage = converterSize.decrease(image);
        ImagePreserver imagePreserver = new ImagePreserver();
        PathGenerator pathGenerator = new PathGenerator(filePath);
        imagePreserver.saveImage(resizedImage, pathGenerator.getPath("resized", "png"));
        generatedFiles.add(pathGenerator.getPath("resized", "png"));

        IMosaicElementColorLoader mosaicElementColorLoader = DMCElementColors.getInstance();
        IImagePixelColorsToElementColors colorConverter = new ImagePixelColorsToElementColors(new PixelColorToElementColor(), mosaicElementColorLoader);
        colorConverter.convertPixelColorsToElementColors(resizedImage);

        Palette palette = new Palette(new AlphabeticSymbols(), resizedImage);

        imagePreserver.saveImage(palette.generatePreview(), pathGenerator.getPath("preview", "png"));
        generatedFiles.add(pathGenerator.getPath("preview", "png"));

        PDFPalettePreserver pdfPalettePreserver = new PDFPalettePreserver(palette);
        pdfPalettePreserver.savePDF(pathGenerator.getPath("palette", "pdf"));
        generatedFiles.add(pathGenerator.getPath("palette", "pdf"));

        ITableMap tableMap = new TableMap(palette);
        String[][] forPrint = tableMap.getTableMap();

        PDFMapPreserver pdfMapPreserver = new PDFMapPreserver(forPrint);
        pdfMapPreserver.savePDF(pathGenerator.getPath("map", "pdf"));
        generatedFiles.add(pathGenerator.getPath("map", "pdf"));

        return generatedFiles;
    }

}

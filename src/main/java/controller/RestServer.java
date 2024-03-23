package controller;

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
import controller.request.RequestFile;
import model.tableMap.AlphabeticSymbols;
import model.tableMap.ITableMap;
import model.tableMap.TableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class RestServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/health-check")
    public HttpStatus healthCheck() {
        return HttpStatus.OK;
    }

    @PostMapping(value = "/process-file", headers = "content-type=application/json")
    public Map<String, String> processPng(@RequestBody RequestFile file) throws ImageIsNotPNGExceptions, IOException, DocumentException, MosaicIsTooBigException {
        String filePath = file.getFilePath();
        logger.info("Start processing: " + filePath);
        Map<String, String> responseFiles = new HashMap<>();

        ImageLoader loader = new ImageLoader();
        Image image = loader.loadImage(filePath);

        IElementToPixel converterSize = new ImageToElements();
        Image resizedImage = converterSize.decrease(image);
        ImagePreserver imagePreserver = new ImagePreserver();
        PathGenerator pathGenerator = new PathGenerator(filePath);
        String resizedFileOutput = pathGenerator.getPath("resized", "png");
        imagePreserver.saveImage(resizedImage, resizedFileOutput);
        responseFiles.put("resized", resizedFileOutput);
        logger.info("Generated resized file: " + resizedFileOutput);

        IMosaicElementColorLoader mosaicElementColorLoader = DMCElementColors.getInstance();
        IImagePixelColorsToElementColors colorConverter = new ImagePixelColorsToElementColors(new PixelColorToElementColor(), mosaicElementColorLoader);
        colorConverter.convertPixelColorsToElementColors(resizedImage);

        Palette palette = new Palette(new AlphabeticSymbols(), resizedImage);

        String previewFileOutput = pathGenerator.getPath("preview", "png");
        imagePreserver.saveImage(palette.generatePreview(), previewFileOutput);
        responseFiles.put("preview", previewFileOutput);
        logger.info("Generated preview file: " + previewFileOutput);

        PDFPalettePreserver pdfPalettePreserver = new PDFPalettePreserver(palette);
        String paletteOutputFile = pathGenerator.getPath("palette", "pdf");
        pdfPalettePreserver.savePDF(paletteOutputFile);
        responseFiles.put("palette", paletteOutputFile);
        logger.info("Generated palette file: " + paletteOutputFile);

        ITableMap tableMap = new TableMap(palette);
        String[][] tableMapForSave = tableMap.getTableMap();

        PDFMapPreserver pdfMapPreserver = new PDFMapPreserver(tableMapForSave);
        String mapOutputFile = pathGenerator.getPath("map", "pdf");
        pdfMapPreserver.savePDF(mapOutputFile);
        responseFiles.put("map", mapOutputFile);
        logger.info("Generated map file: " + paletteOutputFile);

        logger.info("End processing: " + filePath);

        return responseFiles;
    }

}

package io.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import exceptions.MosaicIsTooBigException;
import model.color.Color;
import model.elementColorsLoader.DMCElementColors;
import model.elementColorsLoader.IMosaicElementColorLoader;
import model.palette.Palette;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFPalettePreserver implements IPDFPreserver {

    private final Palette palette;

    public PDFPalettePreserver(Palette palette) {
        this.palette = palette;
    }

    @Override
    public void savePDF(String filepath) throws FileNotFoundException, DocumentException, MosaicIsTooBigException {
        int tableColumns = 4; // color id, color, countElements
        Font font = new Font(Font.FontFamily.HELVETICA, 14);
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter.getInstance(document, new FileOutputStream(filepath));
        document.open();
        PdfPTable table = new PdfPTable(tableColumns);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setTotalWidth(50);
        table.addCell(new Phrase("Color ID", font));
        table.addCell(new Phrase("DMC Color ID", font));
        table.addCell(new Phrase("Color", font));
        table.addCell(new Phrase("Element quantity", font));
        IMosaicElementColorLoader mosaicElementColorLoader = DMCElementColors.getInstance();


        for (Color color: palette.getColorSymbolMap().keySet()) {
            for (int i = 0; i < tableColumns; i++) {
                PdfPCell cell = new PdfPCell();
                switch (i) {
                    case 0:
                        cell.setPhrase(new Phrase(palette.getColorSymbolMap().get(color), font));
                        break;
                    case 1:
                        cell.setPhrase(new Phrase(mosaicElementColorLoader.getColors().get(color), font));
                        break;
                    case 2:
                        cell.setBackgroundColor(new BaseColor(color.getRed(), color.getGreen(), color.getBlue()));
                        break;
                    case 3:
                        cell.setPhrase(new Phrase(palette.getColorCountMap().get(color).toString(), font));
                }

                table.addCell(cell);
            }
        }
        document.add(table);
        document.close();
    }
}

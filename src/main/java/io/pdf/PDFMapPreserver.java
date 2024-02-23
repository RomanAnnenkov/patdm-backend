package io.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import exceptions.MosaicIsTooBigException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFMapPreserver implements IPDFPreserver {
    private final String[][] tableMap;

    private final float elementSide = 7f;

    public PDFMapPreserver(String[][] tableMap) {
        this.tableMap = tableMap;
    }

    @Override
    public void savePDF(String filepath) throws FileNotFoundException, DocumentException, MosaicIsTooBigException {
        int tableColumns = tableMap[0].length;
        int tableRows = tableMap.length;
        Rectangle pageSize = getPageSize(tableColumns, tableRows);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 5);
        Font smallFont = new Font(Font.FontFamily.HELVETICA, 3);
        Document document = new Document(pageSize, 5, 5, 5, 5);
        PdfWriter.getInstance(document, new FileOutputStream(filepath));
        document.open();
        PdfPTable table = new PdfPTable(tableColumns);
        table.setTotalWidth(elementSide * tableColumns);
        table.setLockedWidth(true);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        for (int i = 0; i < tableMap.length; i++) {
            for (int j = 0; j < tableMap[0].length; j++) {
                String element = tableMap[i][j];
                Font font = element.length() == 1 ? normalFont : smallFont;
                table.addCell(createCell(element, font));
            }
        }
        document.add(table);
        document.close();
    }

    private PdfPCell createCell(String str, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(str, font));
        cell.setBorderWidth(0.1f);
        cell.setPadding(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(0);
        cell.setFixedHeight(elementSide);
        return cell;
    }

    private Rectangle getPageSize(int wideInElement, int heightInElement) throws MosaicIsTooBigException {
        // a4 210x297 мм | 180 - 266 мм => 80 120 el
        // a3 297x420 мм | 266 - 390 мм => 120 176 el
        // a2 420x594 мм | 390 - 564 мм => 176 256 el
        // a1 594x841 мм | 564 - 810 мм => 256 368 el
        // a0 841x1189 мм | 810 - 1158 мм => 368 526 el
        // element side 2.2 мм
        if (wideInElement <= 80 && heightInElement <= 120) {
            return PageSize.A4;
        }
        if (wideInElement <= 120 && heightInElement <= 176) {
            return PageSize.A3;
        }
        if (wideInElement <= 176 && heightInElement <= 256) {
            return PageSize.A2;
        }
        if (wideInElement <= 256 && heightInElement <= 368) {
            return PageSize.A1;
        }
        if (wideInElement <= 368 && heightInElement <= 526) {
            return PageSize.A0;
        }
        throw new MosaicIsTooBigException("need format more than A0");
    }

}

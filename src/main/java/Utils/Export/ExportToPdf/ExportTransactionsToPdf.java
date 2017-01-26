package Utils.Export.ExportToPdf;

import Controller.ControllerApplication;
import Domain.MoneyPlace;
import Domain.Transaction;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;
import java.util.ArrayList;

public class ExportTransactionsToPdf {

    private ControllerApplication controller;

    public ExportTransactionsToPdf(ControllerApplication controller) {
        this.controller = controller;
    }

    public void createPdf(String path, ArrayList<Transaction> transactions, String message, Boolean withMoneyPlace, Boolean withTotal) throws IOException {
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 40);
        addParagraph(document, "Pdf created by WIMM application developed by Toader Mihai!\n");
        addParagraph(document, message);
        addTransactions(document,transactions);
        if (withMoneyPlace) {
            addParagraph(document,"Money places:\n");
            addMoneyPlaceTable(document);
        }
        if (withTotal){
            addParagraph(document,"Total " + controller.getTotal());
        }
        document.close();
    }

    private void addParagraph(Document document, String message){
        Paragraph a = new Paragraph(message);
        document.add(a);
    }

    private void addToTable(Table table, PdfFont font, String somethingToAdd){
        table.addCell(
                new Cell().add(
                        new Paragraph(somethingToAdd).setFont(font)));
    }


    private void addTransactions(Document document, ArrayList<Transaction> transactions) throws IOException {
        Table table = new Table(new float[]{1, 2, 4, 8, 3, 2, 8});
        table.setWidthPercent(100);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        String []headers = {"Nr.","Type","Amount","From","Date","Time","Name"};
        for (int i =0; i<headers.length; i++){
            table.addHeaderCell(
                    new Cell().add(
                            new Paragraph(headers[i]).setFont(bold)));
        }

        Integer i = 0;
        for (Transaction t:transactions){
            i++;
            addToTable(table, font, i.toString());
            addToTable(table, font, t.getType());
            addToTable(table, font, t.getAmount().toString());
            addToTable(table, font, controller.getControllerMoneyPlaces().getMoneyPlaceName(t.getIdMoneyPlace()));
            addToTable(table, font, t.getDate().toString());
            addToTable(table, font, t.getTime().toString());
            addToTable(table, font, t.getName());
        }

        document.add(table);
    }

    private void addMoneyPlaceTable(Document document) throws IOException {
        Table table = new Table(new float[]{1, 8, 4});
        table.setWidthPercent(100);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        String []headers = {"Nr.","Name","Amount"};
        for (int i =0; i<headers.length; i++){
            table.addHeaderCell(
                    new Cell().add(
                            new Paragraph(headers[i]).setFont(bold)));
        }

        Integer i = 0;
        for (MoneyPlace mP:controller.getAllMoneyPlaces()){
            i++;
            addToTable(table, font, i.toString());
            addToTable(table, font, mP.getName());
            addToTable(table, font, mP.getAmount().toString());
        }

        document.add(table);
    }

}

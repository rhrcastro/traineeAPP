package bsi.mpoo.traineeufrpe;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfViewer {
    Context Mcontext;
    private File pdf;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSub = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);

    public PdfViewer(Context context){
        this.Mcontext = context;
    }

    public void open_document(){
        create_file();
        try{
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdf));
            document.open();

        }catch (Exception e){
            Log.e("open_document", e.toString());
        }
    }

    public void close_doc(){
        document.close();
    }

    private void create_file(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if (!folder.exists()){
            folder.mkdir();

            pdf = new File(folder, "PdfViewer.pdf");
        }
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitle(String title, String subtitle, String date){
        try{
            paragraph = new Paragraph();
            addChild(new Paragraph(title, fTitle));
            addChild(new Paragraph(subtitle, fSub));
            addChild(new Paragraph("Data " +date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitle", e.toString());
        }
    }
    private void addChild (Paragraph childParagraph ){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    public void CreateTable (String[] header, ArrayList<String[]> usuario){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSub));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.BLUE);
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < usuario.size(); indexR++) {
                String[] row = usuario.get(indexR);
                for (int indexJ = 0; indexJ < usuario.size(); indexJ++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexJ]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
            }catch (Exception e) {
                Log.e("CreateTable", e.toString());
            }

    }
}

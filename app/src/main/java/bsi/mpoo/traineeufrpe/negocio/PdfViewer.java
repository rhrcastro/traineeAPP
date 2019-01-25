package bsi.mpoo.traineeufrpe.negocio;

import android.content.Context;
import android.content.Intent;
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

import bsi.mpoo.traineeufrpe.gui.estagiario.curriculo.VerCurriculo;


public class PdfViewer {
    private final Context Mcontext;
    private File pdf;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private final Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 40, Font.BOLD);
    private final Font fSub = new Font(Font.FontFamily.TIMES_ROMAN, 38, Font.BOLD);
    private final Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);
    private final Font fTextNormal = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private final Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 35, Font.BOLD);
    LoginServices loginServices = new LoginServices(null);

    public PdfViewer(Context context){
        this.Mcontext = context;
    }

    public void open_document(){
        File file = create_file();
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

    private File create_file(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if (!folder.exists())
            folder.mkdir();
        pdf = new File(folder, "PdfViewer.pdf");
        return folder;
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
            addChild(new Paragraph(date, fHighText));
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

    public void addText(String text){
        try {
            paragraph = new Paragraph(text, fTextNormal);
            paragraph.setSpacingAfter(1);
            paragraph.setSpacingBefore(3);
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
                for (indexC= 0; indexC < header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
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

    public void ViewPdf(){
        Intent intent = new Intent(Mcontext, VerCurriculo.class);
        intent.putExtra("path", pdf.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Mcontext.startActivity(intent);
    }

    public File ReturnPdf(){
        return pdf.getAbsoluteFile();
    }

}

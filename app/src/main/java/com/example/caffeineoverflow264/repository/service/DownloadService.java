package com.example.caffeineoverflow264.repository.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import androidx.core.app.NotificationCompat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String instructions = intent.getStringExtra("instructions");
        String title = intent.getStringExtra("title");
        String ingredientString = intent.getStringExtra("ingredients");
        try {
            File dest = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),title+".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Caffeine Overflow");
            document.addCreator("Caffeine Overflow");
            float bodyFontSize = 16.0f;
            float titleFontSize = 26.0f;

            //Title of the page
            BaseFont titleFontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
            Font pageTitleFont = new Font(titleFontName, titleFontSize, Font.NORMAL, BaseColor.BLACK);

            Chunk pageTitleChunk = new Chunk(title, pageTitleFont);
            Paragraph pageTitleParagraph = new Paragraph(pageTitleChunk);
            pageTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(pageTitleParagraph);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            BaseFont bodyFontName = BaseFont.createFont("assets/fonts/brandon_light.otf", "UTF-8", BaseFont.EMBEDDED);
            Font bodyFont = new Font(bodyFontName, bodyFontSize, Font.NORMAL, BaseColor.DARK_GRAY);

            //Ingredients
            Chunk ingredientsChunk = new Chunk(ingredientString,bodyFont);
            Paragraph ingredientsParagraph = new Paragraph(ingredientsChunk);
            document.add(ingredientsParagraph);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            //Instructions
            Chunk instructionsChunk = new Chunk(instructions, bodyFont);
            Paragraph instructionsParagraph = new Paragraph(instructionsChunk);
            document.add(instructionsParagraph);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            document.close();
            displayNotification("File Saved!!","Download complete.");

        }
        catch (Exception ex){
            ex.printStackTrace();
            displayNotification("Error","Download error. Please try again later");
        }

    }

    private void displayNotification(String title,String contentText){
        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0,1000})
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }





}
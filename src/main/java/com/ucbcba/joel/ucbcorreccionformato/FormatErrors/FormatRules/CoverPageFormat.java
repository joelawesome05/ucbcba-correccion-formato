package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;


import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.CoverFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CoverPageFormat implements FormatRule {

    private PDDocument pdfdocument;
    private GeneralSeeker seeker;
    private AtomicLong counter;

    public CoverPageFormat(PDDocument pdfdocument, AtomicLong counter){
        this.pdfdocument = pdfdocument;
        this.seeker = new GeneralSeeker(pdfdocument);
        this.counter = counter;
    }

    @Override
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        int numberOfLines = getNumberOfLines(page);
        int numberOfLineTypeOfWork = getLineTypeOfWork(page,numberOfLines-3);
        int cont=1;

        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(page);
        pdfStripper.setEndPage(page);
        pdfStripper.setParagraphStart("\n");
        pdfStripper.setSortByPosition(true);
        //Recorre la página linea por linea
        for (String line : pdfStripper.getText(pdfdocument).split(pdfStripper.getParagraphStart())) {
            String arr[] = line.split(" ", 2);
            // Condicional si encuentra una linea en blanco
            if (!arr[0].equals("")) {
                String wordLine = line.trim();
                List<WordsProperties> words = seeker.findWordsFromAPage(page, wordLine);
                if (words.size()!=0) {
                    List<String> comments = new ArrayList<>();
                    if (cont == 1) {
                        comments = new CoverFormat(words.get(0), 18, "Centrado", true, false, true,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }
                    if (cont == 2) {
                        comments = new CoverFormat(words.get(0), 16, "Centrado", true, false, true,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }
                    if (cont == 3 || cont == 4) {
                        comments = new CoverFormat(words.get(0), 14, "Centrado", true, false, false,true).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }

                    if (cont >= 5 && cont < numberOfLineTypeOfWork) {
                        comments = new CoverFormat(words.get(0), 16, "Centrado", true, false, false,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }

                    if (cont == numberOfLineTypeOfWork) {
                        comments = new CoverFormat(words.get(0), 12, "Derecho", false, true, false,true).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }

                    if (cont > numberOfLineTypeOfWork && cont <= numberOfLines - 2) {
                        comments = new CoverFormat(words.get(0), 14, "Centrado", true, false, false,true).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }

                    if (cont == numberOfLines || cont == numberOfLines - 1) {
                        comments = new CoverFormat(words.get(0), 12, "Centrado", false, false, false,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                    }
                }

                cont++;
            }
        }
        return formatErrors;
    }


    private int getNumberOfLines(int page) throws IOException {
        int cont=0;
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(page);
        pdfStripper.setEndPage(page);
        pdfStripper.setParagraphStart("\n");
        pdfStripper.setSortByPosition(true);
        for (String line : pdfStripper.getText(pdfdocument).split(pdfStripper.getParagraphStart())) {
            String arr[] = line.split(" ", 2);
            if (!arr[0].equals("")) {
                cont++;
            }
        }
        return cont;
    }

    private int getLineTypeOfWork(int page,int lineTypeOfWork) throws IOException {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(page);
        pdfStripper.setEndPage(page);
        pdfStripper.setParagraphStart("\n");
        pdfStripper.setSortByPosition(true);
        int cont=0;
        for (String line : pdfStripper.getText(pdfdocument).split(pdfStripper.getParagraphStart())) {
            String arr[] = line.split(" ", 2);
            if (!arr[0].equals("")) {
                cont++;
                if (line.contains("Licenciatura") || line.contains("licenciatura") || line.contains("LICENCIATURA")){
                    return cont;
                }
            }
        }
        return lineTypeOfWork;
    }

    private void reportFormatErrors(List<String> comments, List<WordsProperties> words, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(counter).reportFormatError(comments, words.get(0), pageWidth, pageHeight, page));
        }
    }
}
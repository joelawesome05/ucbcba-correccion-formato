package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TableFigureIndexFormat implements FormatRule {
    private PDDocument pdfdocument;
    private GeneralSeeker seeker;
    private AtomicLong counter;

    public TableFigureIndexFormat(PDDocument pdfdocument, AtomicLong counter){
        this.pdfdocument = pdfdocument;
        this.seeker = new GeneralSeeker(pdfdocument);
        this.counter = counter;
    }

    @Override
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        List<FormatErrorReport> formatErrors = new ArrayList<>();

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
                List<String> comments = new ArrayList<>();
                if (words.size() == 0) {
                    wordLine = Normalizer.normalize(wordLine, Normalizer.Form.NFD);
                    wordLine = wordLine.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                    words = seeker.findWordsFromAPage(page, wordLine);
                    if (words.size() == 0) {
                        continue;
                    }
                }
                if (wordLine.length() > 3) {
                    if (wordLine.contains("ÍNDICE DE TABLAS") || wordLine.contains("ÍNDICE DE FIGURAS")) {
                        comments = new Format(words.get(0),12,"Centrado",true,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                        continue;
                    }
                    comments = new Format(words.get(0),12,"Izquierdo",false,false).getFormatErrors(pageWidth);
                    reportFormatErrors(comments, words, formatErrors, pageWidth, pageHeight, page);
                }

            }
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, List<WordsProperties> words, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(counter).reportFormatError(comments, words.get(0), pageWidth, pageHeight, page));
        }
    }
}

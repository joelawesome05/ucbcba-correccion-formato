package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.TableFormat;
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

public class TablesFormat implements FormatRule {
    private PDDocument pdfdocument;
    private GeneralSeeker seeker;
    private AtomicLong counter;
    private AtomicLong tableNumeration;

    public TablesFormat(PDDocument pdfdocument, AtomicLong counter, AtomicLong tableNumeration){
        this.pdfdocument = pdfdocument;
        this.seeker = new GeneralSeeker(pdfdocument);
        this.counter = counter;
        this.tableNumeration = tableNumeration;
    }

    @Override
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        boolean foundTable = false;

        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(page);
        pdfStripper.setEndPage(page);
        pdfStripper.setParagraphStart("\n");
        pdfStripper.setSortByPosition(true);
        //Recorre la página linea por linea
        for (String line : pdfStripper.getText(pdfdocument).split(pdfStripper.getParagraphStart())) {
            String arr[] = line.split(" ", 2);
            if (!arr[0].equals("")){
                if (arr[0].contains("Tabla") || arr[0].contains("TABLA")){
                    List<String> comments = new ArrayList<>();
                    foundTable = true;
                    String wordLine = line.trim();
                    List<WordsProperties> words = seeker.findWordsFromAPage(page, wordLine);
                    for (WordsProperties word: words){
                        comments = new TableFormat(word,12,"Centrado",true,false,tableNumeration.get() ).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, word, formatErrors, pageWidth, pageHeight, page);
                        tableNumeration.incrementAndGet();
                    }
                }

                if ((arr[0].contains("Fuente:") || arr[0].contains("FUENTE:")) && foundTable ){
                    List<String> comments = new ArrayList<>();
                    String wordLine = line.trim();
                    List<WordsProperties> words = seeker.findWordsFromAPage(page, wordLine);
                    for (WordsProperties word: words){
                        comments = new Format(word,12,"Centrado",false,false).getFormatErrors(pageWidth);
                        reportFormatErrors(comments, word, formatErrors, pageWidth, pageHeight, page);
                    }
                }
            }
        }
        return formatErrors;
    }
    private void reportFormatErrors(List<String> comments, WordsProperties word, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(counter).reportFormatError(comments, word, pageWidth, pageHeight, page));
        }
    }
}

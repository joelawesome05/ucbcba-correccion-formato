package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.GeneralIndexFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.SameLevelTittle;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import com.ucbcba.joel.ucbcorreccionformato.General.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GeneralIndexPageFormat implements FormatRule {

    private PDDocument pdfdocument;
    private GeneralSeeker seeker;
    private AtomicLong idHighlights;
    private int generalIndexPageStart;
    private int generalIndexPageEnd;

    public GeneralIndexPageFormat(PDDocument pdfdocument, AtomicLong idHighlights,int generalIndexPageStart,int generalIndexPageEnd) {
        this.pdfdocument = pdfdocument;
        this.seeker = new GeneralSeeker(pdfdocument);
        this.idHighlights = idHighlights;
        this.generalIndexPageStart = generalIndexPageStart;
        this.generalIndexPageEnd = generalIndexPageEnd;
    }

    @Override
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        List<FormatErrorReport> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        int lineStart = 0;

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        if (generalIndexPageStart == page){
            if (!wordsLines.isEmpty()){
                List<String> formatErrorscomments = new ArrayList<>();
                Format titles = new TittleFormat(wordsLines.get(0),12,"Centrado",true,false,"ÍNDICE GENERAL");
                formatErrorscomments = titles.getFormatErrorComments(pageWidth);
                reportFormatErrors(formatErrorscomments, wordsLines.get(0), formatErrors, pageWidth, pageHeight, page);
                lineStart++;
            }
        }

        for(int line=lineStart; line<wordsLines.size(); line++){
            List<String> formatErrorscomments = new ArrayList<>();
            WordsProperties currentWordLine = wordsLines.get(line);
            String arr[] = currentWordLine.toString().split(" ", 2);
            String currentNumeration = arr[0];
            int numberOfPoints = countChar(currentNumeration, '.');
            if (numberOfPoints == 0) {
                if (isValidTittle(currentNumeration)){
                    Format titles = new GeneralIndexFormat(currentWordLine,12,"Izquierdo",true,false,true,0);
                    formatErrorscomments = titles.getFormatErrorComments(pageWidth);
                }else{
                    if (!isAnnex(currentNumeration)){
                        formatErrorscomments.add("Sea un título válido según la guía");
                    }
                }
            } else {
                if (currentNumeration.endsWith(".")) {
                    if (numberOfPoints == 1) {
                        Format chapterTitles = new GeneralIndexFormat(currentWordLine, 12, "Izquierdo", true, false, true, 0);
                        formatErrorscomments = chapterTitles.getFormatErrorComments(pageWidth);
                    }
                    if (numberOfPoints == 2) {
                        Format chapterSubTitles = new GeneralIndexFormat(currentWordLine, 12, "Izquierdo", true, false, false, 1);
                        formatErrorscomments = chapterSubTitles.getFormatErrorComments(pageWidth);
                    }
                    if (numberOfPoints == 3) {
                        Format sectionTitles = new GeneralIndexFormat(currentWordLine, 12, "Izquierdo", true, true, false, 2);
                        formatErrorscomments = sectionTitles.getFormatErrorComments(pageWidth);
                    }
                    if (numberOfPoints == 4) {
                        Format sectionSubTitles = new GeneralIndexFormat(currentWordLine, 12, "Izquierdo", false, true, false, 3);
                        formatErrorscomments = sectionSubTitles.getFormatErrorComments(pageWidth);
                    }
                    if (currentNumeration.endsWith(".1.")) {
                        SameLevelTittle sameLevelTittle = new SameLevelTittle(currentWordLine, page, generalIndexPageEnd, currentNumeration, seeker);
                        formatErrorscomments.addAll(sameLevelTittle.getFormatErrorComments());
                    }
                } else {
                    formatErrorscomments.add("La numeración termine con un punto y no con un número");
                }
            }
            reportFormatErrors(formatErrorscomments, currentWordLine, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page));
        }
    }

    private int countChar(String str, char c)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count;
    }

    private boolean isValidTittle(String tittle){
        boolean resp = false;
        if (tittle.contains("INTRODUCCIÓN") || tittle.contains("CONCLUSIONES") || tittle.contains("RECOMENDACIONES") || tittle.contains("BIBLIOGRAFÍA") || tittle.contains("ANEXOS")){
            resp = true;
        }
        return resp;
    }

    private boolean isAnnex(String tittle){
        boolean resp = false;
        if (tittle.contains("Anexo") || tittle.contains("ANEXO")){
            resp = true;
        }
        return resp;
    }
}

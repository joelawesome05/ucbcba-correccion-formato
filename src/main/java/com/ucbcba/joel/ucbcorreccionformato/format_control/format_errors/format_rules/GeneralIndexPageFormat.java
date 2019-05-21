package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.GeneralIndexFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.SameLevelTittle;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.page_calibration.WordsFinder;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GeneralIndexPageFormat implements FormatRule {

    private PDDocument pdfdocument;
    private WordsFinder seeker;
    private AtomicLong idHighlights;
    private int generalIndexPageStart;
    private int generalIndexPageEnd;
    private static final String IZQUIERDO = "Izquierdo";

    public GeneralIndexPageFormat(PDDocument pdfdocument, AtomicLong idHighlights,int generalIndexPageStart,int generalIndexPageEnd) {
        this.pdfdocument = pdfdocument;
        this.seeker = new WordsFinder(pdfdocument);
        this.idHighlights = idHighlights;
        this.generalIndexPageStart = generalIndexPageStart;
        this.generalIndexPageEnd = generalIndexPageEnd;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        Format generalIndextitle = new TittleFormat(12,"Centrado",pageWidth,true,"INDICE GENERAL");
        Format titles = new GeneralIndexFormat(12, IZQUIERDO,true,false,true,0);
        Format chapterTitles = new GeneralIndexFormat(12, IZQUIERDO, true, false, true, 0);
        Format chapterSubTitles = new GeneralIndexFormat(12, IZQUIERDO,true, false, false, 1);
        Format sectionTitles = new GeneralIndexFormat(12, IZQUIERDO,true, true, false, 2);
        Format sectionSubTitles = new GeneralIndexFormat(12, IZQUIERDO, false, true, false, 3);


        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutAnyNumeration(page);

        if (generalIndexPageStart == page && !wordsLines.isEmpty()){
            List<String> formatErrorscomments = generalIndextitle.getFormatErrorComments(wordsLines.get(0));
            reportFormatErrors(formatErrorscomments, wordsLines.get(0), formatErrors, pageWidth, pageHeight, page);
            wordsLines.remove(0);
        }
        List<List<WordsProperties>> titlesGeneralIndex = getGeneralIndexTittles(wordsLines);

        for(List<WordsProperties> title: titlesGeneralIndex){
            if(!title.isEmpty()) {
                WordsProperties firstLine = title.get(0);
                List<String> formatErrorscomments = new ArrayList<>();
                String[] arr = firstLine.toString().split(" ", 2);
                String currentNumeration = arr[0];
                int numberOfPoints = countChar(currentNumeration, '.');
                if (numberOfPoints == 0) {
                    if (isValidTittle(currentNumeration)) {
                        formatErrorscomments = titles.getFormatErrorComments(firstLine);
                    } else {
                        if (!isAnnex(currentNumeration)) {
                            formatErrorscomments.add("Sea un título válido según la guía");
                        }
                    }
                } else {
                    if (!currentNumeration.endsWith(".")) {
                        formatErrorscomments.add("La numeración termine con un punto y no con un número");
                        numberOfPoints++;
                    }
                    if (currentNumeration.endsWith(".1.")) {
                        SameLevelTittle sameLevelTittle = new SameLevelTittle(page, generalIndexPageEnd, seeker);
                        formatErrorscomments.addAll(sameLevelTittle.getFormatErrorComments(firstLine, currentNumeration));
                    }
                    if (numberOfPoints == 1) {
                        formatErrorscomments.addAll(chapterTitles.getFormatErrorComments(firstLine));
                    }
                    if (numberOfPoints == 2) {
                        formatErrorscomments.addAll(chapterSubTitles.getFormatErrorComments(firstLine));
                    }
                    if (numberOfPoints == 3) {
                        formatErrorscomments.addAll(sectionTitles.getFormatErrorComments(firstLine));
                    }
                    if (numberOfPoints == 4) {
                        formatErrorscomments.addAll(sectionSubTitles.getFormatErrorComments(firstLine));
                    }
                }
                reportFormatErrors(formatErrorscomments, title, formatErrors, pageWidth, pageHeight, page);
            }
        }
        WordsProperties numeration = getterWordLines.getIndexCoverPageNumeration(page);
        if(numeration!=null){
            List<String> formatErrorscomments = new ArrayList<>();
            formatErrorscomments.add("Esta sección no tenga numeración");
            reportFormatErrors(formatErrorscomments, numeration, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page,"indiceGeneral"));
        }
    }

    private void reportFormatErrors(List<String> comments, List<WordsProperties> words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page,"indiceGeneral"));
        }
    }

    private int countChar(String str, char c) {
        int count = 0;
        for(int i=0; i < str.length(); i++) {
            if(str.charAt(i) == c)
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

    private List<List<WordsProperties>> getGeneralIndexTittles(List<WordsProperties> wordsLines){
        List<List<WordsProperties>> resp = new ArrayList<>();
        List<WordsProperties> currentTittle = new ArrayList<>();
        for(WordsProperties line:wordsLines){
            currentTittle.add(line);
            if(line.length() > 1 && Character.isDigit(line.charAt(line.length() - 1))) {
                resp.add(currentTittle);
                currentTittle = new ArrayList<>();
            }
        }
        resp.add(currentTittle);
        return resp;
    }
}

package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.GeneralIndexFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.SameLevelTittle;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.page_calibration.WordsFinder;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
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

        Format titles = new GeneralIndexFormat(12, IZQUIERDO,true,false,true,0);
        Format chapterTitles = new GeneralIndexFormat(12, IZQUIERDO, true, false, true, 0);
        Format chapterSubTitles = new GeneralIndexFormat(12, IZQUIERDO,true, false, false, 1);
        Format sectionTitles = new GeneralIndexFormat(12, IZQUIERDO,true, true, false, 2);
        Format sectionSubTitles = new GeneralIndexFormat(12, IZQUIERDO, false, true, false, 3);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> singleLines = getterWordLines.getSingleLinesWithoutAnyNumeration(page);
        controlTittle(page, formatErrors, pageWidth, pageHeight, singleLines);

        List<WordLine> titlesGeneralIndex = getterWordLines.getGeneralIndexTittles(singleLines);

        for(WordLine currentTitle: titlesGeneralIndex){
            List<String> formatErrorscomments = new ArrayList<>();
            String currentNumeration = currentTitle.getNumeration();
            int numberOfPoints = countPoints(currentNumeration);
            if (numberOfPoints == 0) {
                if (isValidTittle(currentNumeration)) {
                    formatErrorscomments = titles.getFormatErrorComments(currentTitle);
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
                    formatErrorscomments.addAll(sameLevelTittle.getFormatErrorComments(currentTitle, currentNumeration));
                }
                if (numberOfPoints == 1) {
                    formatErrorscomments.addAll(chapterTitles.getFormatErrorComments(currentTitle));
                }
                if (numberOfPoints == 2) {
                    formatErrorscomments.addAll(chapterSubTitles.getFormatErrorComments(currentTitle));
                }
                if (numberOfPoints == 3) {
                    formatErrorscomments.addAll(sectionTitles.getFormatErrorComments(currentTitle));
                }
                if (numberOfPoints == 4) {
                    formatErrorscomments.addAll(sectionSubTitles.getFormatErrorComments(currentTitle));
                }
            }
            reportFormatErrors(formatErrorscomments, currentTitle, formatErrors, pageWidth, pageHeight, page);

        }
        WordLine numeration = getterWordLines.getAnyPageNumeration(page);
        controlPageNumeration(numeration, formatErrors, pageWidth, pageHeight, page);
        return formatErrors;
    }

    private void controlTittle(int page, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, List<SingleLine> singleLines) {
        Format generalIndextitle = new TittleFormat(12,"Centrado",pageWidth,true,"ÍNDICE GENERAL");
        if (generalIndexPageStart == page && !singleLines.isEmpty()){
            List<SingleLine> tittleLine = new ArrayList<>();
            tittleLine.add(singleLines.get(0));
            WordLine tittle = new WordLine(tittleLine);
            List<String> formatErrorscomments = generalIndextitle.getFormatErrorComments(tittle);
            reportFormatErrors(formatErrorscomments,tittle, formatErrors, pageWidth, pageHeight, page);
            singleLines.remove(0);
        }
    }

    private void reportFormatErrors(List<String> comments, WordLine words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            ReportFormatError reporter = new ReportFormatError(idHighlights);
            formatErrors.add(reporter.reportFormatError(comments, words, pageWidth, pageHeight, page,"indiceGeneral"));
        }
    }

    private void controlPageNumeration(WordLine numeration, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if(numeration!=null){
            List<String> formatErrorscomments = new ArrayList<>();
            formatErrorscomments.add("Esta sección no tenga numeración");
            reportFormatErrors(formatErrorscomments, numeration, formatErrors, pageWidth, pageHeight, page);
        }
    }


    private int countPoints(String str) {
        int count = 0;
        for(int i=0; i < str.length(); i++) {
            if(str.charAt(i) == '.')
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

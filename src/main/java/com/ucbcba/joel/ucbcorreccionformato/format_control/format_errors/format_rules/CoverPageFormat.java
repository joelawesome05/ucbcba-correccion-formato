package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;


import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.CoverFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CoverPageFormat implements FormatRule {

    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private static final String CENTRADO = "Centrado";

    public CoverPageFormat(PDDocument pdfdocument, AtomicLong idHighlights){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordLine> wordsLines = getterWordLines.getCoverPageElements(page);
        int line=0;
        controlInstitutionName(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlRegionalAcademic(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlDepartmentCareerAuthor(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlDepartmentCareerAuthor(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlTittleWork(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlTypeWork(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlDepartmentCareerAuthor(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlCountryDate(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        line++;
        controlCountryDate(wordsLines, formatErrors, line, pageWidth, pageHeight, page);
        WordLine numeration = getterWordLines.getAnyPageNumeration(page);
        controlPageNumeration(numeration, formatErrors, pageWidth, pageHeight, page);
        return formatErrors;
    }

    private void controlInstitutionName(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format nameOfTheInstitution =  new CoverFormat(18, CENTRADO, pageWidth,true, false, true,false);
            List<String> formatErrorscomments = nameOfTheInstitution.getFormatErrorComments(wordsLines.get(line));
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlRegionalAcademic(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format regionalAcademicUnit = new CoverFormat( 16, CENTRADO, pageWidth,true, false, true,false);
            List<String> formatErrorscomments = regionalAcademicUnit.getFormatErrorComments(wordsLines.get(line));
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlDepartmentCareerAuthor(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format department = new CoverFormat(14, CENTRADO,pageWidth, true, false, false,true);
            List<String> formatErrorscomments = department.getFormatErrorComments(wordsLines.get(line));
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlTittleWork(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format titleOfTheWork = new CoverFormat(16, CENTRADO, pageWidth,true, false, false,false);
            List<String> formatErrorscomments = titleOfTheWork.getFormatErrorComments(wordsLines.get(line));
            List<SingleLine> lines = wordsLines.get(line).getLines();
            if(lines.size() > 3){
                formatErrorscomments.add("El título del trabajo no debe exceder de tres líneas");
            }
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlTypeWork(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format typeOfTheWork = new CoverFormat( 12, "Derecho", pageWidth,false, true, false,true);
            List<String> formatErrorscomments = typeOfTheWork.getFormatErrorComments(wordsLines.get(line));
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlCountryDate(List<WordLine> wordsLines, List<FormatErrorResponse> formatErrors, int line, float pageWidth, float pageHeight, int page) {
        if(line < wordsLines.size()){
            Format cityAndCountry = new CoverFormat(12, CENTRADO, pageWidth, false, false, false, false);
            List<String> formatErrorscomments = cityAndCountry.getFormatErrorComments(wordsLines.get(line));
            reportFormatErrors(formatErrorscomments, wordsLines.get(line), formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void controlPageNumeration(WordLine numeration, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if(numeration!=null){
            List<String> formatErrorscomments = new ArrayList<>();
            formatErrorscomments.add("Esta sección no tenga numeración");
            reportFormatErrors(formatErrorscomments, numeration, formatErrors, pageWidth, pageHeight, page);
        }
    }

    private void reportFormatErrors(List<String> comments, WordLine words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            ReportFormatError reporter = new ReportFormatError(idHighlights);
            formatErrors.add(reporter.reportFormatError(comments, words, pageWidth, pageHeight, page,"caratula"));
        }
    }
}
package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatRules;


import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.CoverFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CoverPageFormat implements FormatRule {

    private PDDocument pdfdocument;
    private AtomicLong idHighlights;

    public CoverPageFormat(PDDocument pdfdocument, AtomicLong idHighlights){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        Format nameOfTheInstitution =  new CoverFormat(18, "Centrado", pageWidth,true, false, true,false);
        Format regionalAcademicUnit = new CoverFormat( 16, "Centrado", pageWidth,true, false, true,false);
        Format DepartmentAndCareer = new CoverFormat(14, "Centrado",pageWidth, true, false, false,true);
        Format titleOfTheWork = new CoverFormat(16, "Centrado", pageWidth,true, false, false,false);
        Format typeOfTheWork = new CoverFormat( 12, "Derecho", pageWidth,false, true, false,true);
        Format authorName = new CoverFormat(14, "Centrado", pageWidth,true, false, false,true);
        Format cityCountryAndYear = new CoverFormat(12, "Centrado", pageWidth, false, false, false, false);


        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        int totalLines = wordsLines.size()-1;
        int lineTypeOfWork = getLineTypeOfWork(wordsLines,totalLines-3);

        for(int line=0; line<=totalLines; line++){
            List<String> formatErrorscomments = new ArrayList<>();
            WordsProperties currentWordLine = wordsLines.get(line);
            if (line == 0) {
                formatErrorscomments = nameOfTheInstitution.getFormatErrorComments(currentWordLine);
            }
            if (line == 1) {
                formatErrorscomments = regionalAcademicUnit.getFormatErrorComments(currentWordLine);
            }
            if (line == 2 || line == 3) {
                formatErrorscomments = DepartmentAndCareer.getFormatErrorComments(currentWordLine);
            }

            if (line >= 4 && line < lineTypeOfWork) {
                formatErrorscomments = titleOfTheWork.getFormatErrorComments(currentWordLine);
            }

            if (line == lineTypeOfWork) {
                formatErrorscomments = typeOfTheWork.getFormatErrorComments(currentWordLine);
            }

            if (line > lineTypeOfWork && line <= totalLines - 2) {
                formatErrorscomments = authorName.getFormatErrorComments(currentWordLine);
            }

            if (line == totalLines || line == totalLines - 1) {
                formatErrorscomments = cityCountryAndYear.getFormatErrorComments(currentWordLine);
            }
            reportFormatErrors(formatErrorscomments, currentWordLine, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private int getLineTypeOfWork(List<WordsProperties> wordsLines,int lineTypeOfWork) {
        for(int line=0; line<wordsLines.size(); line++){
            String currentWordLine = wordsLines.get(line).toString();
            if (currentWordLine.contains("Licenciatura") || currentWordLine.contains("licenciatura") || currentWordLine.contains("LICENCIATURA")){
                return line;
            }
        }
        return lineTypeOfWork;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page));
        }
    }
}
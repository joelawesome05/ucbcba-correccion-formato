package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;


import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.CoverFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
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
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        List<FormatErrorReport> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        int totalLines = wordsLines.size()-1;
        int lineTypeOfWork = getLineTypeOfWork(wordsLines,totalLines-3);

        for(int line=0; line<=totalLines; line++){
            List<String> formatErrorscomments = new ArrayList<>();
            WordsProperties currentWordLine = wordsLines.get(line);
            if (line == 0) {
                Format nameOfTheInstitution =  new CoverFormat(currentWordLine, 18, "Centrado", true, false, true,false);
                formatErrorscomments = nameOfTheInstitution.getFormatErrorComments(pageWidth);
            }
            if (line == 1) {
                Format regionalAcademicUnit = new CoverFormat(currentWordLine, 16, "Centrado", true, false, true,false);
                formatErrorscomments = regionalAcademicUnit.getFormatErrorComments(pageWidth);
            }
            if (line == 2 || line == 3) {
                Format DepartmentAndCareer = new CoverFormat(currentWordLine, 14, "Centrado", true, false, false,true);
                formatErrorscomments = DepartmentAndCareer.getFormatErrorComments(pageWidth);
            }

            if (line >= 4 && line < lineTypeOfWork) {
                Format titleOfTheWork = new CoverFormat(currentWordLine, 16, "Centrado", true, false, false,false);
                formatErrorscomments = titleOfTheWork.getFormatErrorComments(pageWidth);
            }

            if (line == lineTypeOfWork) {
                Format typeOfTheWork = new CoverFormat(currentWordLine, 12, "Derecho", false, true, false,true);
                formatErrorscomments = typeOfTheWork.getFormatErrorComments(pageWidth);
            }

            if (line > lineTypeOfWork && line <= totalLines - 2) {
                Format authorName = new CoverFormat(currentWordLine, 14, "Centrado", true, false, false,true);
                formatErrorscomments = authorName.getFormatErrorComments(pageWidth);
            }

            if (line == totalLines || line == totalLines - 1) {
                Format cityCountryAndYear = new CoverFormat(currentWordLine, 12, "Centrado", false, false, false, false);
                formatErrorscomments = cityCountryAndYear.getFormatErrorComments(pageWidth);
            }
            reportFormatErrors(formatErrorscomments, currentWordLine, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private int getLineTypeOfWork(List<WordsProperties> wordsLines,int lineTypeOfWork) throws IOException {
        for(int line=0; line<wordsLines.size(); line++){
            String currentWordLine = wordsLines.get(line).toString();
            if (currentWordLine.contains("Licenciatura") || currentWordLine.contains("licenciatura") || currentWordLine.contains("LICENCIATURA")){
                return line;
            }
        }
        return lineTypeOfWork;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page));
        }
    }
}
package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.PageFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PageNumerationFormat implements FormatRule {
    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private int correctPageNumeration;

    public PageNumerationFormat(PDDocument pdfdocument, AtomicLong idHighlights,int correctPageNumeration) {
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.correctPageNumeration = correctPageNumeration;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        Format numerationFormat = new PageFormat(12,"Derecho",pageWidth,correctPageNumeration);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        WordsProperties pageNumerationPage = getterWordLines.getPageNumeration(page);

        if(pageNumerationPage!=null){
            List<String> comments = numerationFormat.getFormatErrorComments(pageNumerationPage);
            reportFormatErrors(comments, pageNumerationPage, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties pageNumerationPage, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, pageNumerationPage, pageWidth, pageHeight, page,"numeracion"));
        }
    }
}

package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.PageFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import com.ucbcba.joel.ucbcorreccionformato.General.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
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
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        WordsProperties pageNumerationPage = getterWordLines.getPageNumeration(page);

        if(pageNumerationPage!=null){
            Format numerationFormat = new PageFormat(pageNumerationPage,12,"Derecho",correctPageNumeration);
            List<String> comments = numerationFormat.getFormatErrorComments(pageWidth);
            reportFormatErrors(comments, pageNumerationPage, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties pageNumerationPage, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, pageNumerationPage, pageWidth, pageHeight, page));
        }
    }
}

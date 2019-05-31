package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TableFigureIndexFormat implements FormatRule {
    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private int pageStart;
    private String indexName;

    public TableFigureIndexFormat(PDDocument pdfdocument, AtomicLong idHighlights, int pageStart, String indexName){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.pageStart = pageStart;
        this.indexName = indexName;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> singleLines = getterWordLines.getSingleLinesWithoutAnyNumeration(page);

        controlTittle(singleLines, formatErrors, pageWidth, pageHeight, page);

        List<WordLine> titlesIndex = getterWordLines.getGeneralIndexTittles(singleLines);

        Format normalFormat = new Format( 12);
        for(WordLine currentTitle: titlesIndex){
            List<String> formatErrorscomments = normalFormat.getFormatErrorComments(currentTitle);
            reportFormatErrors(formatErrorscomments, currentTitle, formatErrors, pageWidth, pageHeight, page);
        }

        WordLine numeration = getterWordLines.getAnyPageNumeration(page);
        controlPageNumeration(numeration, formatErrors, pageWidth, pageHeight, page);
        return formatErrors;
    }

    private void controlTittle(List<SingleLine> singleLines, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        Format title = new TittleFormat(12,"Centrado",pageWidth,true,"ÍNDICE DE "+indexName);
        if (pageStart == page && !singleLines.isEmpty()){
            List<SingleLine> tittleLine = new ArrayList<>();
            tittleLine.add(singleLines.get(0));
            WordLine tittle = new WordLine(tittleLine);
            List<String> formatErrorscomments = title.getFormatErrorComments(tittle);
            reportFormatErrors(formatErrorscomments,tittle, formatErrors, pageWidth, pageHeight, page);
            singleLines.remove(0);
        }
    }

    private void reportFormatErrors(List<String> comments, WordLine words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            ReportFormatError reporter = new ReportFormatError(idHighlights);
            formatErrors.add(reporter.reportFormatError(comments, words, pageWidth, pageHeight, page,indexName));
        }
    }

    private void controlPageNumeration(WordLine numeration, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if(numeration!=null){
            List<String> formatErrorscomments = new ArrayList<>();
            formatErrorscomments.add("Esta sección no tenga numeración");
            reportFormatErrors(formatErrorscomments, numeration, formatErrors, pageWidth, pageHeight, page);
        }
    }
}

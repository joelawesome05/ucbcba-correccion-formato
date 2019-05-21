package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;
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

        Format title = new TittleFormat(12,"Centrado",pageWidth,true,"INDICE DE "+indexName);
        Format normalFormat = new Format( 12);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutAnyNumeration(page);

        if (pageStart == page && !wordsLines.isEmpty()){
            List<String> formatErrorscomments = title.getFormatErrorComments(wordsLines.get(0));
            reportFormatErrors(formatErrorscomments, wordsLines.get(0), formatErrors, pageWidth, pageHeight, page);
            wordsLines.remove(0);
        }

        for(WordsProperties wordLine: wordsLines){
            List<String> formatErrorscomments = normalFormat.getFormatErrorComments(wordLine);
            reportFormatErrors(formatErrorscomments, wordLine, formatErrors, pageWidth, pageHeight, page);
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
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page,indexName));
        }
    }
}

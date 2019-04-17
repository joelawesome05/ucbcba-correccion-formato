package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
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
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        List<FormatErrorReport> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        int lineStart = 0;

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        if (pageStart == page){
            if (!wordsLines.isEmpty()){
                List<String> formatErrorscomments = new ArrayList<>();
                Format titles = new TittleFormat(wordsLines.get(0),12,"Centrado",true,"ÍNDICE DE "+indexName);
                formatErrorscomments = titles.getFormatErrorComments(pageWidth);
                reportFormatErrors(formatErrorscomments, wordsLines.get(0), formatErrors, pageWidth, pageHeight, page);
                lineStart++;
            }
        }

        for(int line=lineStart; line<wordsLines.size(); line++){
            List<String> formatErrorscomments = new ArrayList<>();
            WordsProperties currentWordLine = wordsLines.get(line);
            Format chapterSubTitles = new Format(currentWordLine, 12);
            formatErrorscomments = chapterSubTitles.getFormatErrorComments(pageWidth);
            reportFormatErrors(formatErrorscomments, wordsLines.get(0), formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page));
        }
    }
}

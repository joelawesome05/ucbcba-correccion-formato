package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.FigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.SourceTableTittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.TableFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
import com.ucbcba.joel.ucbcorreccionformato.General.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.General.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FiguresTablesFormat implements FormatRule {
    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private AtomicLong tableNumeration;
    private AtomicLong figureNumeration;

    public FiguresTablesFormat(PDDocument pdfdocument, AtomicLong idHighlights, AtomicLong tableNumeration, AtomicLong figureNumeration){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.tableNumeration = tableNumeration;
        this.figureNumeration = figureNumeration;
    }

    @Override
    public List<FormatErrorReport> getFormatErrors(int page) throws IOException {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);

        for(WordsProperties wordLine:wordsLines){
            List<String> formatErrorscomments = new ArrayList<>();
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")){
                Format tableTittle = new TableFormat(wordLine,12,"Centrado",true,tableNumeration.get() );
                formatErrorscomments = tableTittle.getFormatErrorComments(pageWidth);
                tableNumeration.incrementAndGet();
            }
            if (firstWordLine.contains("Figura")){
                Format figureTittle = new FigureFormat(wordLine,12,"Centrado",true, figureNumeration.get());
                formatErrorscomments = figureTittle.getFormatErrorComments(pageWidth);
                figureNumeration.incrementAndGet();
            }
            if (firstWordLine.contains("Fuente:")){
                Format source = new SourceTableTittleFormat(wordLine,12,"Centrado",false);
                formatErrorscomments = source.getFormatErrorComments(pageWidth);
            }
            reportFormatErrors(formatErrorscomments, wordLine, formatErrors, pageWidth, pageHeight, page);
        }

        return formatErrors;
    }
    private void reportFormatErrors(List<String> comments, WordsProperties word, List<FormatErrorReport> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, word, pageWidth, pageHeight, page));
        }
    }
}

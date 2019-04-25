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

        Format source = new SourceTableTittleFormat(12,"Centrado",pageWidth,false);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);

        for(WordsProperties wordLine:wordsLines){
            List<String> formatErrorscomments = new ArrayList<>();
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")){
                Format tableTittle = new TableFormat(12,"Centrado",pageWidth,true,tableNumeration.get());
                formatErrorscomments = tableTittle.getFormatErrorComments(wordLine);
                tableNumeration.incrementAndGet();
            }
            if (firstWordLine.contains("Figura")){
                Format figureTittle = new FigureFormat(12,"Centrado",pageWidth,true, figureNumeration.get());
                formatErrorscomments = figureTittle.getFormatErrorComments(wordLine);
                figureNumeration.incrementAndGet();
            }
            if (firstWordLine.contains("Fuente:")){
                formatErrorscomments = source.getFormatErrorComments(wordLine);
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

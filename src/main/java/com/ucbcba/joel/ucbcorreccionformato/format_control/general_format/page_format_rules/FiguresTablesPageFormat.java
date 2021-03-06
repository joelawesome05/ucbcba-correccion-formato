package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.FigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.SourceTableFigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.TableFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FiguresTablesPageFormat implements PageFormatRule {

    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private AtomicLong tableNumeration;
    private AtomicLong figureNumeration;
    private Integer lastPage;
    private static final String CENTRADO = "Centrado";
    private static final String TABLA = "Tabla";
    private static final String FIGURA = "Figura";
    private static final String TABLA_UPPERCASE = "TABLA";
    private static final String FIGURA_UPPERCASE = "FIGURA";
    private static final String FUENTE = "Fuente:";

    public FiguresTablesPageFormat(PDDocument pdfdocument, AtomicLong idHighlights, AtomicLong tableNumeration, AtomicLong figureNumeration, Integer lastPage){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.tableNumeration = tableNumeration;
        this.figureNumeration = figureNumeration;
        this.lastPage = lastPage;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        Format source = new SourceTableFigureFormat(12, CENTRADO,pageWidth,false);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordLine> wordsLines = getterWordLines.getWordLines(page);
        for(int pos=0; pos<wordsLines.size(); pos++){
            WordLine wordLine = wordsLines.get(pos);
            List<String> formatErrorscomments = new ArrayList<>();
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if ((containsWordTable(firstWordLine)) && isValidTittle(pos,page)){
                Format tableTittle = new TableFormat(12, CENTRADO, pageWidth, true, tableNumeration.get());
                formatErrorscomments = tableTittle.getFormatErrorComments(wordLine);
                tableNumeration.incrementAndGet();
            }
            if ((containsWordFigure(firstWordLine)) && isValidTittle(pos,page)){
                Format figureTittle = new FigureFormat(12, CENTRADO, pageWidth, true, figureNumeration.get());
                formatErrorscomments = figureTittle.getFormatErrorComments(wordLine);
                figureNumeration.incrementAndGet();
            }
            if (firstWordLine.contains(FUENTE)){
                formatErrorscomments = source.getFormatErrorComments(wordLine);
            }
            reportFormatErrors(formatErrorscomments, wordLine, formatErrors, pageWidth, pageHeight, page);
        }

        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordLine words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            ReportFormatError reporter = new ReportFormatError(idHighlights);
            formatErrors.add(reporter.reportFormatError(comments, words, pageWidth, pageHeight, page,"tablaFigura"));
        }
    }

    private boolean isValidTittle(int pos, int currentPage) throws IOException {
        boolean resp = hasTheKeyWords(currentPage,pos);
        if(resp){
            return isValidCurrentPage(currentPage,pos);
        }else {
            for (int page = currentPage + 1; page < lastPage; page++) {
                if(hasTheKeyWords(page)) {
                    return isValidOtherPage(page);
                }
            }
        }
        return false;
    }

    public boolean hasTheKeyWords(int page,int posStart) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(page);
        SingleLine wordLine;
        for(int pos=posStart+1; pos<wordsLines.size(); pos++){
            wordLine = wordsLines.get(pos);
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (containsWordTable(firstWordLine)) {
                return true;
            }
            if (containsWordFigure(firstWordLine)) {
                return true;
            }
            if (firstWordLine.contains(FUENTE)) {
                return true;
            }
        }
        return resp;
    }

    public boolean hasTheKeyWords(int page) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(page);
        for(SingleLine wordLine: wordsLines){
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (containsWordTable(firstWordLine)) {
                return true;
            }
            if (containsWordFigure(firstWordLine)) {
                return true;
            }
            if (firstWordLine.contains(FUENTE)) {
                return true;
            }
        }
        return resp;
    }
    private boolean isValidCurrentPage(int page, int posStart) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(page);
        SingleLine wordLine;
        for(int pos=posStart+1; pos<wordsLines.size(); pos++){
            wordLine = wordsLines.get(pos);
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (containsWordTable(firstWordLine)) {
                return false;
            }
            if (containsWordFigure(firstWordLine)) {
                return false;
            }
            if (firstWordLine.contains(FUENTE)) {
                return true;
            }
        }
        return resp;
    }

    private boolean isValidOtherPage(int page) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(page);
        for (SingleLine wordLine : wordsLines) {
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (containsWordTable(firstWordLine)) {
                return false;
            }
            if (containsWordFigure(firstWordLine)) {
                return false;
            }
            if (firstWordLine.contains(FUENTE)) {
                return true;
            }
        }
        return resp;
    }

    private boolean containsWordTable(String firstWordLine) {
        return firstWordLine.contains(TABLA) || firstWordLine.contains(TABLA_UPPERCASE);
    }

    private boolean containsWordFigure(String firstWordLine) {
        return firstWordLine.contains(FIGURA) || firstWordLine.contains(FIGURA_UPPERCASE);
    }
}

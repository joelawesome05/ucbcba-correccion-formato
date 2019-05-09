package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.FigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.SourceTableFigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.TableFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;
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
    private Integer lastPage;

    public FiguresTablesFormat(PDDocument pdfdocument, AtomicLong idHighlights, AtomicLong tableNumeration, AtomicLong figureNumeration,Integer lastPage){
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

        Format source = new SourceTableFigureFormat(12,"Centrado",pageWidth,false);

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);
        WordsProperties wordLine;
        for(int pos=0; pos<wordsLines.size(); pos++){
            wordLine = wordsLines.get(pos);
            List<String> formatErrorscomments = new ArrayList<>();
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")){
                if(isValidTittle(pos,page)) {
                    Format tableTittle = new TableFormat(12, "Centrado", pageWidth, true, tableNumeration.get());
                    formatErrorscomments = tableTittle.getFormatErrorComments(wordLine);
                    tableNumeration.incrementAndGet();
                }
            }
            if (firstWordLine.contains("Figura")){
                if(isValidTittle(pos,page)) {
                    Format figureTittle = new FigureFormat(12, "Centrado", pageWidth, true, figureNumeration.get());
                    formatErrorscomments = figureTittle.getFormatErrorComments(wordLine);
                    figureNumeration.incrementAndGet();
                }
            }
            if (firstWordLine.contains("Fuente:")){
                formatErrorscomments = source.getFormatErrorComments(wordLine);
            }
            reportFormatErrors(formatErrorscomments, wordLine, formatErrors, pageWidth, pageHeight, page);
        }

        return formatErrors;
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
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);
        WordsProperties wordLine;
        for(int pos=posStart+1; pos<wordsLines.size(); pos++){
            wordLine = wordsLines.get(pos);
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")) {
                return true;
            }
            if (firstWordLine.contains("Figura")) {
                return true;
            }
            if (firstWordLine.contains("Fuente:")) {
                return true;
            }
        }
        return resp;
    }

    public boolean hasTheKeyWords(int page) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);
        for(WordsProperties wordLine: wordsLines){
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")) {
                return true;
            }
            if (firstWordLine.contains("Figura")) {
                return true;
            }
            if (firstWordLine.contains("Fuente:")) {
                return true;
            }
        }
        return resp;
    }
    private boolean isValidCurrentPage(int page, int posStart) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);
        WordsProperties wordLine;
        for(int pos=posStart+1; pos<wordsLines.size(); pos++){
            wordLine = wordsLines.get(pos);
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")) {
                return false;
            }
            if (firstWordLine.contains("Figura")) {
                return false;
            }
            if (firstWordLine.contains("Fuente:")) {
                return true;
            }
        }
        return resp;
    }

    private boolean isValidOtherPage(int page) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(page);
        for (WordsProperties wordLine : wordsLines) {
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Tabla")) {
                return false;
            }
            if (firstWordLine.contains("Figura")) {
                return false;
            }
            if (firstWordLine.contains("Fuente:")) {
                return true;
            }
        }
        return resp;
    }
    private void reportFormatErrors(List<String> comments, WordsProperties word, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, word, pageWidth, pageHeight, page,"tablaFigura"));
        }
    }
}

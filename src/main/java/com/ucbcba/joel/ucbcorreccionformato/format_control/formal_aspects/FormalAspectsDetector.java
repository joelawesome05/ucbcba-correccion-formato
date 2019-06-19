package com.ucbcba.joel.ucbcorreccionformato.format_control.formal_aspects;

import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.Format;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormalAspectsDetector {
    private PDDocument pdfdocument;

    public FormalAspectsDetector(PDDocument pdfdocument) {
        this.pdfdocument = pdfdocument;
    }


    public List<FormalAspectsResponse> getFormalAspectsResponses() throws IOException {
        List<FormalAspectsResponse> resp = new ArrayList<>();
        int page = (pdfdocument.getNumberOfPages()+1)/2;
        resp.add(getFormatSheetSize(page));
        resp.add(getFormatFont(page));
        resp.add(getFormatMargin(page));
        resp.add(getFormatNumeration(page));
        return resp;
    }


    public FormalAspectsResponse getFormatSheetSize(int page){
        String formatSize = "Tamaño de hoja carta";
        boolean isCorrectSize = false;
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        if (pageWidth == 612.0 && pageHeight == 792.0){
            isCorrectSize = true;
        }
        return new FormalAspectsResponse(formatSize,isCorrectSize);
    }

    public FormalAspectsResponse getFormatMargin(int page) throws IOException {
        String formatMargin = "Margen 3cm (derecho, inferior y superior) 3.5cm (izquierdo)";
        boolean isCorrectMargin = true;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLinesWithoutPageNumeration(page);
        for(SingleLine wordLine:wordsLines){
            if (wordLine.getX() < 95 || wordLine.getYPlusHeight() < 75 || wordLine.getXPlusWidth() > 535 ){
                isCorrectMargin = false;
            }
        }
        return new FormalAspectsResponse(formatMargin,isCorrectMargin);
    }

    public FormalAspectsResponse getFormatFont(int page) throws IOException {
        String formatFont = "Tipo de letra: Times New Roman 12";
        boolean isCorrectFont = true;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLinesWithoutPageNumeration(page);
        Format formatGeneral = new Format(12);
        WordLine words = new WordLine(wordsLines);
        List<String> errorComments = formatGeneral.getFormatErrorComments(words);
        if(!errorComments.isEmpty()) {
            isCorrectFont = false;
        }

        return new FormalAspectsResponse(formatFont,isCorrectFont);
    }

    public FormalAspectsResponse getFormatNumeration(int page) throws IOException {
        String formatNumeration = "Numeración parte inferior";
        boolean isCorrectNumeration = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        WordLine wordsLine = getterWordLines.getPageNumeration(page);
        if(wordsLine!=null){
            isCorrectNumeration = true;
        }
        return new FormalAspectsResponse(formatNumeration,isCorrectNumeration);
    }

}

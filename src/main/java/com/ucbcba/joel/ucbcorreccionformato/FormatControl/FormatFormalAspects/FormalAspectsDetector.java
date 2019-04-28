package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatFormalAspects;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormalAspectsDetector {
    private PDDocument pdfdocument;

    public FormalAspectsDetector(PDDocument pdfdocument) {
        this.pdfdocument = pdfdocument;
    }


    public List<FormalAspectsResponse> getFormalAspectsResponses(Integer indexPageEnd, Integer annexedPage) throws IOException {
        List<FormalAspectsResponse> resp = new ArrayList<>();
        int page = (indexPageEnd+annexedPage)/2;

        resp.add(getFormatSheetSize(page));
        resp.add(getFormatFont(page));
        resp.add(getFormatLineSpacing(page));
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
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        for(WordsProperties wordLine:wordsLines){
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
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        Format basicFormat = new Format(12);
        for(WordsProperties wordLine:wordsLines){
            List<String> comments = basicFormat.getBasicFormatErrorComments(wordLine);
            if(comments.size()!=0){
                isCorrectFont = false;
            }
        }
        return new FormalAspectsResponse(formatFont,isCorrectFont);
    }

    public FormalAspectsResponse getFormatNumeration(int page) throws IOException {
        String formatNumeration = "Numeración parte inferior";
        boolean isCorrectNumeration = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        WordsProperties wordsLine = getterWordLines.getPageNumeration(page);
        if(wordsLine!=null){
            isCorrectNumeration = true;
        }
        return new FormalAspectsResponse(formatNumeration,isCorrectNumeration);
    }

    public FormalAspectsResponse getFormatLineSpacing(int page) throws IOException {
        String formatLineSpacing = "Espaciado entre lineas 1,5";
        boolean isCorrectLineSpacing = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        double lineSpacing = getterWordLines.getLineSpacing(page);
        if (lineSpacing <= 22.0 && lineSpacing >= 20.0){
            isCorrectLineSpacing = true;
        }
        return new FormalAspectsResponse(formatLineSpacing,isCorrectLineSpacing);
    }
}

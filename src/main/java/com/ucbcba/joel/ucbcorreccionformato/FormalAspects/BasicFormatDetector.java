package com.ucbcba.joel.ucbcorreccionformato.FormalAspects;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.General.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicFormatDetector {
    private PDDocument pdfdocument;

    public BasicFormatDetector(PDDocument pdfdocument) {
        this.pdfdocument = pdfdocument;
    }


    public List<BasicFormatReport> getBasicFormatReport(Integer indexPageEnd, Integer annexedPage) throws IOException {
        List<BasicFormatReport> resp = new ArrayList<>();
        int page = (indexPageEnd+annexedPage)/2;

        resp.add(getFormatSheetSize(page));
        resp.add(getFormatFont(page));
        resp.add(getFormatLineSpacing(page));
        resp.add(getFormatMargin(page));
        resp.add(getFormatNumeration(page));

        return resp;
    }


    public BasicFormatReport getFormatSheetSize(int page){
        String formatSize = "Tamaño de hoja carta";
        boolean isCorrectSize = false;
        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();
        if (pageWidth == 612.0 && pageHeight == 792.0){
            isCorrectSize = true;
        }
        return new BasicFormatReport(formatSize,isCorrectSize);
    }

    public BasicFormatReport getFormatMargin(int page) throws IOException {
        String formatMargin = "Margen 3cm (derecho, inferior y superior) 3.5cm (izquierdo)";
        boolean isCorrectMargin = true;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        for(WordsProperties wordLine:wordsLines){
            if (wordLine.getX() < 95 || wordLine.getYPlusHeight() < 75 || wordLine.getXPlusWidth() > 535 ){
                isCorrectMargin = false;
            }
        }
        return new BasicFormatReport(formatMargin,isCorrectMargin);
    }

    public BasicFormatReport getFormatFont(int page) throws IOException {
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
        return new BasicFormatReport(formatFont,isCorrectFont);
    }

    public BasicFormatReport getFormatNumeration(int page) throws IOException {
        String formatNumeration = "Numeración parte inferior";
        boolean isCorrectNumeration = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        WordsProperties wordsLine = getterWordLines.getPageNumeration(page);
        if(wordsLine!=null){
            isCorrectNumeration = true;
        }
        return new BasicFormatReport(formatNumeration,isCorrectNumeration);
    }

    public BasicFormatReport getFormatLineSpacing(int page) throws IOException {
        String formatLineSpacing = "Espaciado entre lineas 1,5";
        boolean isCorrectLineSpacing = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        double maxLineSpacing = 0;
        Integer maxCount = -1;
        Map<Double, Integer> lineSpacingCount = new HashMap<>();
        List<WordsProperties> wordsLines = getterWordLines.getWordLinesWithoutPageNumeration(page);
        for (int i=1 ; i < wordsLines.size() ; i++){
            double currentLineSpacing = Math.ceil(wordsLines.get(i).getY() - wordsLines.get(i-1).getY());

            if (!lineSpacingCount.containsKey(currentLineSpacing)) { lineSpacingCount.put(currentLineSpacing, 0); }
            int count = lineSpacingCount.get(currentLineSpacing) + 1;
            if (count > maxCount) {
                maxLineSpacing = currentLineSpacing;
                maxCount = count;
            }
            lineSpacingCount.put(currentLineSpacing, count);
        }


        if (maxLineSpacing == 21.0){
            isCorrectLineSpacing = true;
        }
        return new BasicFormatReport(formatLineSpacing,isCorrectLineSpacing);
    }
}

package com.ucbcba.joel.ucbcorreccionformato.General;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetterWordLines {
    private PDDocument pdfdocument;

    public GetterWordLines(PDDocument pdfdocument) {
        this.pdfdocument = pdfdocument;
    }

    private List<List<TextPosition>> getNormalWordLines(int page) throws IOException {
        final  List<List<TextPosition>> listWordPositionSequences = new ArrayList<>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                if (!textPositions.isEmpty()) {
                    if (textPositions.get(0).toString().equals(" ")){
                        boolean isBlankLine = true;
                        int contSpaces= 0;
                        for(TextPosition textPosition:textPositions){
                            if(!textPosition.toString().equals(" ") || !isBlankLine){
                                isBlankLine = false;
                            }else{
                                contSpaces++;
                            }
                        }
                        if(!isBlankLine){
                            textPositions = textPositions.subList(contSpaces,textPositions.size());
                            listWordPositionSequences.add(textPositions);
                        }
                    }else{
                        listWordPositionSequences.add(textPositions);
                    }
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(pdfdocument);
        return listWordPositionSequences;
    }

    private List<WordsProperties> cleanWordLines(List<List<TextPosition>> list){
        List<WordsProperties> resp = new ArrayList<WordsProperties>();
        List<TextPosition> textPositions = new ArrayList<TextPosition>();
        if(!list.isEmpty()){
            if(!list.get(0).isEmpty()) {
                float currentY = list.get(0).get(0).getYDirAdj();
                textPositions = list.get(0);
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i).get(0).getYDirAdj() == currentY) {
                        TextPosition firstChar = list.get(i).get(0);
                        TextPosition space = new TextPosition(firstChar.getRotation(),firstChar.getPageWidth(),firstChar.getPageHeight(),firstChar.getTextMatrix(),firstChar.getEndX(),firstChar.getEndY(),firstChar.getHeight(),firstChar.getWidth(),firstChar.getWidthOfSpace()," ",firstChar.getCharacterCodes(), firstChar.getFont(),firstChar.getFontSize(),(int)firstChar.getFontSizeInPt());
                        list.get(i).add(0,space);
                        textPositions.addAll(list.get(i));
                    } else {
                        resp.add(new WordsProperties(textPositions));
                        currentY = list.get(i).get(0).getYDirAdj();
                        textPositions = list.get(i);
                    }
                }
            }
            textPositions.remove(textPositions.size()-1);
            resp.add(new WordsProperties(textPositions));
        }
        return resp;
    }

    public List<WordsProperties> getWordLines(int page) throws IOException {
        return cleanWordLines(getNormalWordLines(page));
    }

    public List<WordsProperties> getWordLinesWithoutPageNumeration(int page) throws IOException {
        List<WordsProperties> wordLine = new ArrayList<WordsProperties>();
        wordLine = getWordLines(page);
        if(!wordLine.isEmpty()) {
            WordsProperties lastLine = wordLine.get(wordLine.size() - 1);
            if (isPageNumeration(lastLine)) {
                wordLine.remove(wordLine.size() - 1);
            }
            return wordLine;
        }
        return wordLine;
    }

    private boolean isPageNumeration(WordsProperties lastLine){
        boolean resp = true;
        String pageNumeration = lastLine.toString();
        for(int pos=0; pos<pageNumeration.length();pos++){
            if (!Character.isDigit(pageNumeration.charAt(pos))){
                resp = false;
            }
        }
        return resp;
    }
    public WordsProperties getPageNumeration(int page) throws IOException {
        List<WordsProperties> wordLine = new ArrayList<WordsProperties>();
        wordLine = getWordLines(page);
        if(!wordLine.isEmpty()) {
            WordsProperties lastLine = wordLine.get(wordLine.size() - 1);
            if (isPageNumeration(lastLine)) {
                return lastLine;
            }
        }
        return null;
    }

    public List<List<WordsProperties>> getBiographyLines(int page) throws IOException {
        List<List<WordsProperties>> biographyLines = new ArrayList<>();
        List<WordsProperties> wordsLines = getWordLinesWithoutPageNumeration(page);
        WordsProperties currentWordline = wordsLines.get(0);
        List<WordsProperties> currentBiography = new ArrayList<>();
        currentBiography.add(currentWordline);
        double lineSpacing = getLineSpacing(page);
        for (int i=1 ; i < wordsLines.size() ; i++){
            double currentLineSpacing = Math.round(wordsLines.get(i).getY() - wordsLines.get(i-1).getY());
            if(currentLineSpacing <= lineSpacing + 2 && currentLineSpacing >= lineSpacing - 2){
                currentBiography.add(wordsLines.get(i));
            }else{
                biographyLines.add(currentBiography);
                currentBiography = new ArrayList<WordsProperties>();
                currentBiography.add(wordsLines.get(i));
            }
        }
        biographyLines.add(currentBiography);
        return biographyLines;
    }


    private double getLineSpacing(int page) throws IOException {
        double maxLineSpacing = 0;
        Integer maxCount = -1;
        Map<Double, Integer> lineSpacingCount = new HashMap<>();
        List<WordsProperties> wordsLines = getWordLinesWithoutPageNumeration(page);
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

        return maxLineSpacing;
    }

}

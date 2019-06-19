package com.ucbcba.joel.ucbcorreccionformato.format_control;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetterWordLines {
    private PDDocument pdfdocument;

    public GetterWordLines(PDDocument pdfdocument) {
        this.pdfdocument = pdfdocument;
    }

    private List<List<TextPosition>> getLines(int page) throws IOException {
        final  List<List<TextPosition>> listWordPositionSequences = new ArrayList<>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                textPositions = removeBeginningSpaces(textPositions);
                if (!textPositions.isEmpty()) {
                    listWordPositionSequences.add(textPositions);
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

    public List<TextPosition> removeBeginningSpaces(List<TextPosition> list){
        int numberSpaces = 0;
        for (TextPosition aList : list) {
            if (aList.getUnicode().equals(" ")) {
                numberSpaces++;
            } else {
                break;
            }
        }
        list.subList(0, numberSpaces).clear();
        return list;
    }

    private List<SingleLine> cleanToSingleLines(List<List<TextPosition>> list){
        List<SingleLine> resp = new ArrayList<>();
        if(!list.isEmpty() && !list.get(0).isEmpty()){
            float currentY = list.get(0).get(0).getYDirAdj();
            List<TextPosition> currentTextPositions = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).get(0).getYDirAdj() == currentY) {
                    TextPosition firstChar = list.get(i).get(0);
                    TextPosition spaceChar = new TextPosition(firstChar.getRotation(),firstChar.getPageWidth(),firstChar.getPageHeight(),firstChar.getTextMatrix(),firstChar.getEndX(),firstChar.getEndY(),firstChar.getHeight(),firstChar.getWidth(),firstChar.getWidthOfSpace()," ",firstChar.getCharacterCodes(), firstChar.getFont(),firstChar.getFontSize(),(int)firstChar.getFontSizeInPt());
                    list.get(i).add(0,spaceChar);
                    currentTextPositions.addAll(list.get(i));
                } else {
                    currentTextPositions = removeEndingSpaces(currentTextPositions);
                    if (!currentTextPositions.isEmpty()) {
                        resp.add(new SingleLine(currentTextPositions));
                    }
                    currentY = list.get(i).get(0).getYDirAdj();
                    currentTextPositions = list.get(i);
                }
            }
            currentTextPositions = removeEndingSpaces(currentTextPositions);
            if (!currentTextPositions.isEmpty()) {
                resp.add(new SingleLine(currentTextPositions));
            }
        }
        return resp;
    }

    public  List<TextPosition> removeEndingSpaces(List<TextPosition> list){
        for(int pos=list.size()-1; pos>=0 ;pos--){
            if(list.get(pos).getUnicode().equals(" ")){
                list.remove(pos);
            }else{
                return list;
            }
        }
        return list;
    }

    public List<SingleLine> getSingleLines(int page) throws IOException {
        return cleanToSingleLines(getLines(page));
    }

    public List<WordLine> getWordLines(int page) throws IOException {
        List<WordLine> resp = new ArrayList<>();
        List<SingleLine> singleLines = getSingleLines(page);
        for(SingleLine singleLine:singleLines){
            List<SingleLine> lineList = new ArrayList<>();
            lineList.add(singleLine);
            resp.add(new WordLine(lineList));
        }
        return resp;
    }

    public List<SingleLine> getSingleLinesWithoutPageNumeration(int page) throws IOException {
        List<SingleLine> wordLine = getSingleLines(page);
        if(!wordLine.isEmpty()) {
            SingleLine lastLine = wordLine.get(wordLine.size() - 1);
            if (isPageNumeration(lastLine)) {
                wordLine.remove(wordLine.size() - 1);
            }
        }
        return wordLine;
    }

    private boolean isPageNumeration(SingleLine lastLine){
        boolean resp = true;
        String pageNumeration = lastLine.toString();
        for(int pos=0; pos<pageNumeration.length();pos++){
            if (!Character.isDigit(pageNumeration.charAt(pos))){
                resp = false;
            }
        }
        return resp;
    }

    public List<SingleLine> getSingleLinesWithoutAnyNumeration(int page) throws IOException {
        List<SingleLine> wordLine = getSingleLines(page);
        if(!wordLine.isEmpty()) {
            SingleLine lastLine = wordLine.get(wordLine.size() - 1);
            if (lastLine.length() < 4) {
                wordLine.remove(wordLine.size() - 1);
            }
        }
        return wordLine;
    }

    public WordLine getAnyPageNumeration(int page) throws IOException {
        List<SingleLine> wordLine = getSingleLines(page);
        if(!wordLine.isEmpty()) {
            SingleLine lastLine = wordLine.get(wordLine.size() - 1);
            if (lastLine.length() < 4) {
                List<SingleLine> lastWordLine = new ArrayList<>();
                lastWordLine.add(lastLine);
                return new WordLine(lastWordLine);
            }
        }
        return null;
    }

    public WordLine getPageNumeration(int page) throws IOException {
        List<SingleLine> wordLine = getSingleLines(page);
        if(!wordLine.isEmpty()) {
            SingleLine lastLine = wordLine.get(wordLine.size() - 1);
            if (isPageNumeration(lastLine)) {
                List<SingleLine> lastWordLine = new ArrayList<>();
                lastWordLine.add(lastLine);
                return new WordLine(lastWordLine);
            }
        }
        return null;
    }

    public double getLineSpacingBibliography(int page) throws IOException {
        double lineSpacing = 0;
        List<SingleLine> wordsLines = getSingleLinesWithoutPageNumeration(page);
        for (int i=1 ; i < wordsLines.size() ; i++){
            double currentLineSpacing = Math.round(wordsLines.get(i).getY() - wordsLines.get(i-1).getY());
            if(lineSpacing < currentLineSpacing){
                lineSpacing = currentLineSpacing;
            }
        }
        return lineSpacing;
    }

    public List<WordLine> getCoverPageElements(int page) throws IOException {
        List<WordLine> resp = new ArrayList<>();
        List<SingleLine> singleLines = getSingleLinesWithoutAnyNumeration(page);
        int lineTypeOfWork = getLineTypeOfWork(singleLines,singleLines.size()-4);
        List<SingleLine> currentWordLine = new ArrayList<>();
        for(int line=0;line<singleLines.size(); line++){
            currentWordLine.add(singleLines.get(line));
            if (!(line > 3 && line < lineTypeOfWork-1) && !(line > lineTypeOfWork && line < singleLines.size()-3)) {
                resp.add(new WordLine(currentWordLine));
                currentWordLine = new ArrayList<>();
            }
        }
        return resp;
    }

    private int getLineTypeOfWork(List<SingleLine> singleLines, int lineTypeOfWork) {
        for(int line=singleLines.size()-1; line>=0; line--){
            String currentWordLine = singleLines.get(line).toString();
            if (containsWordLicenciatura(currentWordLine) || containsWordPerfil(currentWordLine)){
                return line;
            }
        }
        return lineTypeOfWork;
    }

    private boolean containsWordLicenciatura(String currentWordLine) {
        return currentWordLine.contains("Licenciatura") || currentWordLine.contains("licenciatura") || currentWordLine.contains("LICENCIATURA");
    }

    private boolean containsWordPerfil(String currentWordLine) {
        return currentWordLine.contains("Perfil") || currentWordLine.contains("perfil") || currentWordLine.contains("PERFIL");
    }

    public List<WordLine> getGeneralIndexTittles(List<SingleLine> singleLines){
        List<WordLine> resp = new ArrayList<>();
        List<SingleLine> currentTittle = new ArrayList<>();
        for(SingleLine line:singleLines){
            currentTittle.add(line);
            if((line.length() > 1 && Character.isDigit(line.charAt(line.length() - 1))) || line.getXPlusWidth()<400) {
                resp.add(new WordLine(currentTittle));
                currentTittle = new ArrayList<>();
            }
        }
        return resp;
    }

    public List<WordLine> getBibliographyLines(List<SingleLine> singleLines, double lineSpacing){
        List<WordLine> resp = new ArrayList<>();
        if(!singleLines.isEmpty()) {
            SingleLine currentWordline = singleLines.get(0);
            List<SingleLine> currentBibliography = new ArrayList<>();
            currentBibliography.add(currentWordline);
            for (int i = 1; i < singleLines.size(); i++) {
                double currentLineSpacing = Math.round(singleLines.get(i).getY() - singleLines.get(i - 1).getY());
                if (currentLineSpacing == lineSpacing) {
                    resp.add(new WordLine(currentBibliography));
                    currentBibliography = new ArrayList<>();
                    currentBibliography.add(singleLines.get(i));
                } else {
                    currentBibliography.add(singleLines.get(i));
                }
            }
            resp.add(new WordLine(currentBibliography));
        }
        return resp;
    }

}

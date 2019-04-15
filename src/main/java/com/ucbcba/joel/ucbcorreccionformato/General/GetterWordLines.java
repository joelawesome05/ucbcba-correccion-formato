package com.ucbcba.joel.ucbcorreccionformato.General;

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

    public List<WordsProperties> getWordLines(int page) throws IOException {
        final List<WordsProperties> listWordPositionSequences = new ArrayList<WordsProperties>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties wordsProperties = new WordsProperties(textPositions);
                if (!textPositions.isEmpty()) {
                    if (!textPositions.get(0).toString().equals(" ")){
                        listWordPositionSequences.add(wordsProperties);
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

    public List<WordsProperties> getWordLinesWithoutPageNumeration(int page) throws IOException {
        List<WordsProperties> resp = new ArrayList<WordsProperties>();
        List<WordsProperties> wordLine = new ArrayList<WordsProperties>();
        List<String> lines = new ArrayList<>();
        wordLine = getWordLines(page);
        lines = getWordLinesString(page);
        if(!isPdfdocumentDamaged(wordLine,lines)){
            if(!wordLine.isEmpty()) {
                if (wordLine.get(wordLine.size() - 1).getY() > 720) {
                    wordLine.remove(wordLine.size() - 1);
                }
                return wordLine;
            }
        }
        return resp;
    }

    public WordsProperties getPageNumeration(int page) throws IOException {
        List<WordsProperties> wordLine = new ArrayList<WordsProperties>();
        wordLine = getWordLines(page);
        if(!wordLine.isEmpty()) {
            if (wordLine.get(wordLine.size() - 1).getY() > 720) {
                return wordLine.get(wordLine.size() - 1);
            }
        }
        return null;
    }

    public List<String> getWordLinesString(int page) throws IOException {
        List<String> lines = new ArrayList<>();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(page);
        pdfStripper.setEndPage(page);
        pdfStripper.setSortByPosition(true);
        //Recorre la página linea por linea
        for (String line : pdfStripper.getText(pdfdocument).split("\n")) {
            String arr[] = line.split(" ", 2);
            // Condicional si encuentra una linea en blanco
            if (!arr[0].equals("")) {
                lines.add(line);
            }
        }
        return lines;
    }

    public boolean isPdfdocumentDamaged(List<WordsProperties> wordsLines, List<String> words){
        boolean resp = true;
        if(wordsLines.size() == words.size()){
            resp = false;
        }
        return resp;
    }
}

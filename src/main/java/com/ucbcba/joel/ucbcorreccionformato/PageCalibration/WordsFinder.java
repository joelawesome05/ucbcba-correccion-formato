package com.ucbcba.joel.ucbcorreccionformato.PageCalibration;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.Normalizer;

public class WordsFinder {
    private PDDocument pdfdocument;

    public WordsFinder(PDDocument pdfdocument){
        this.pdfdocument = pdfdocument;
    }

    public boolean isTheWordInThePageAdvanced(int page, String searchWord) throws IOException {
        boolean resp = isTheWordInThePage(page,searchWord);
        if (!resp){
            String newSearchWord = Normalizer.normalize(searchWord, Normalizer.Form.NFD);
            newSearchWord = newSearchWord.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            resp = isTheWordInThePage(page,newSearchWord);
        }
        return resp;
    }

    public boolean isTheWordInThePage(int page, String searchWord) throws IOException {
        final boolean[] resp = {false};
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while (((indexWordFound = string.indexOf(searchWord, index)) > -1) && !resp[0]) {
                    resp[0] = true;
                    index = indexWordFound + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(pdfdocument);
        return resp[0];
    }

    public List<WordsProperties> findWordsFromAPage(int page, String searchWord) throws IOException {
        final List<WordsProperties> listWordPositionSequences = new ArrayList<WordsProperties>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while ((indexWordFound = string.indexOf(searchWord, index)) > -1) {
                    listWordPositionSequences.add(word.subSequence(indexWordFound, indexWordFound + searchWord.length()));
                    index = indexWordFound + 1;
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

    public List<WordsProperties> findWordsFromPages(int pageStart, int pageEnd, String searchWord) throws IOException {
        final List<WordsProperties> listWordPositionSequences = new ArrayList<WordsProperties>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while ((indexWordFound = string.indexOf(searchWord, index)) > -1) {
                    listWordPositionSequences.add(word.subSequence(indexWordFound, indexWordFound + searchWord.length()));
                    index = indexWordFound + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(pageStart);
        stripper.setEndPage(pageEnd);
        stripper.getText(pdfdocument);
        return listWordPositionSequences;
    }
}

package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.PageCalibration.WordsFinder;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SameLevelTittle {
    private int currentPage;
    private int lastIndexPage;

    private WordsFinder wordsFinder;

    public SameLevelTittle(int currentPage, int lastIndexPage, WordsFinder wordsFinder) {
        this.currentPage = currentPage;
        this.lastIndexPage = lastIndexPage;
        this.wordsFinder = wordsFinder;
    }

    public List<String> getFormatErrorComments(WordsProperties word, String currentNumeration) throws IOException {
        List<String> comments =  new ArrayList<>();
        String nextNumeration = removeLastTwoChars(currentNumeration)+"2.";
        List<WordsProperties> nextNumerationFound = wordsFinder.findWordsFromPages(currentPage,lastIndexPage,nextNumeration);
        for(WordsProperties numeration : nextNumerationFound){
            if (numeration.getX() == word.getX()){
                return comments;
            }
        }
        comments.add("Agregar el título "+nextNumeration+" o eliminar el presente título");
        return comments;
    }

    private String removeLastTwoChars(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-2);
    }


}

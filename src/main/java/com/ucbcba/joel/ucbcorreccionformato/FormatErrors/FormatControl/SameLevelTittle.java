package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SameLevelTittle {
    private WordsProperties word;
    private int currentPage;
    private int lastIndexPage;
    private String numeration;
    private GeneralSeeker seeker;

    public SameLevelTittle(WordsProperties word, int currentPage, int lastIndexPage, String numeration, GeneralSeeker seeker) {
        this.word = word;
        this.currentPage = currentPage;
        this.lastIndexPage = lastIndexPage;
        this.numeration = numeration;
        this.seeker = seeker;
    }

    public List<String> getFormatErrors() throws IOException {
        List<String> comments =  new ArrayList<>();
        String nextNumeration = removeLastTwoChars(numeration)+"2.";
        List<WordsProperties> nextNumerationFound = seeker.findWordsFromPages(currentPage,lastIndexPage,nextNumeration);
        for(WordsProperties numeration : nextNumerationFound){
            if (numeration.getX() == word.getX()){
                return comments;
            }
        }
        comments.add("Considerar agregar un título más del mismo nivel");
        return comments;
    }

    private String removeLastTwoChars(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-2);
    }


}

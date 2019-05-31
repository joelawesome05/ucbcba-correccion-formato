package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.page_calibration.WordsFinder;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;

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

    public List<String> getFormatErrorComments(WordLine word, String currentNumeration) throws IOException {
        List<String> comments =  new ArrayList<>();
        String nextNumeration = removeLastTwoChars(currentNumeration)+"2.";
        List<SingleLine> nextNumerationFound = wordsFinder.findWordsFromPages(currentPage,lastIndexPage,nextNumeration);
        for(SingleLine numeration : nextNumerationFound){
            if (numeration.getX()-2 <= word.getX() && numeration.getX()+2 >= word.getX()){
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

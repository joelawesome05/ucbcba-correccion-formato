package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class PageFormat extends Format {
    private int correctPageNumeration;
    public PageFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic, int correctPageNumeration) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.correctPageNumeration = correctPageNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth) {
        List<String> comments =  super.getFormatErrorComments(pageWidth);
        if (!word.toString().contains(Integer.toString(correctPageNumeration))){
            comments.add("Número de página debería ser "+ correctPageNumeration);
        }
        return comments;
    }
}

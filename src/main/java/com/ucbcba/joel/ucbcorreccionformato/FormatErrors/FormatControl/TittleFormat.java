package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class TittleFormat extends  Format {
    private String correctTittle;
    public TittleFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic,String correctTittle) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.correctTittle = correctTittle;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth){
        List<String> comments = super.getFormatErrorComments(pageWidth);;
        if (!word.toString().contains(correctTittle)) {
            comments.add("El título sea: "+correctTittle);
        }
        return comments;
    }
}

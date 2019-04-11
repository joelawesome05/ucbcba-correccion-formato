package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class TableFormat extends Format {

    private long figureNumeration;

    public TableFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic, long figureNumeration) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.figureNumeration = figureNumeration;
    }

    @Override
    public List<String> getFormatErrors(float pageWidth) {
        List<String> comments =  super.getFormatErrors(pageWidth);
        if (!word.toString().contains(Long.toString(figureNumeration))){
            comments.add("Número de tabla debería ser "+ figureNumeration);
        }
        return comments;
    }
}
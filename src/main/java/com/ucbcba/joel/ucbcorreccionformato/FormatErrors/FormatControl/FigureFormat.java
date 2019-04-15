package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class FigureFormat extends Format {

    private long figureNumeration;

    public FigureFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic, long figureNumeration) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.figureNumeration = figureNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth) {
        List<String> comments =  super.getFormatErrorComments(pageWidth);
        if (!word.toString().contains("Figura "+Long.toString(figureNumeration))){
            comments.add("Número de figura debería ser "+ figureNumeration);
        }
        return comments;
    }
}
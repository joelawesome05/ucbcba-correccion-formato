package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;

import java.util.List;

public class FigureFormat extends Format {

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private long figureNumeration;

    public FigureFormat(float fontSize, String alignment, float pageWidth, boolean isBold, long figureNumeration) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.figureNumeration = figureNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(WordsProperties word) {
        List<String> comments =  super.getFormatErrorComments(word);
        if (isBold) {
            if (!word.allCharsHaveFontTypeOf("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.someCharsHaveFontTypeOf("Bold")){
                comments.add("No tenga negrilla");
            }
        }

        if(alignment.equals("Centrado")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100){
                comments.add("Tenga alineación centrada");
            }
        }
        if (!word.toString().contains("Figura "+Long.toString(figureNumeration))){
            comments.add("Número de figura debería ser "+ figureNumeration);
        }
        return comments;
    }
}